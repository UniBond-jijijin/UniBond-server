package com.unibond.unibond.member.service;

import com.unibond.unibond.common.BaseException;
import com.unibond.unibond.common.BaseResponseStatus;
import com.unibond.unibond.common.service.LoginInfoService;
import com.unibond.unibond.common.service.S3Uploader;
import com.unibond.unibond.disease.domain.Disease;
import com.unibond.unibond.disease.repository.DiseaseRepository;
import com.unibond.unibond.member.domain.Member;
import com.unibond.unibond.member.dto.MemberDetailResDto;
import com.unibond.unibond.member.dto.MemberModifyReqDto;
import com.unibond.unibond.member.dto.MemberRegisterReqDto;
import com.unibond.unibond.member.repository.MemberRepository;
import com.unibond.unibond.post.domain.Post;
import com.unibond.unibond.post.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import static com.unibond.unibond.common.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final LoginInfoService loginInfoService;
    private final S3Uploader s3Uploader;
    private final MemberRepository memberRepository;
    private final DiseaseRepository diseaseRepository;
    private final PostRepository postRepository;

    @Transactional
    public Long signupMember(MemberRegisterReqDto registerReqDto, MultipartFile profileImg) throws BaseException {
        try {
            if (memberRepository.existsMemberByNickname(registerReqDto.getNickname())) {
                throw new BaseException(DUPLICATE_MEMBER_NICK);
            }

            Disease disease = diseaseRepository.findById(registerReqDto.getDiseaseId()).orElseThrow(
                    () -> new BaseException(INVALID_DISEASE_ID)
            );
            String imgUrl = s3Uploader.upload(profileImg, "user");
            Member newMember = registerReqDto.toEntity(disease, imgUrl);
            Member savedMember = memberRepository.save(newMember);
            return savedMember.getId();
        } catch (BaseException e) {
            throw e;
        } catch (Exception e) {
            System.err.println(e);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public BaseResponseStatus checkNickNameDuplicate(String nickname) throws BaseException {
        try {
            boolean isPresent = memberRepository.existsMemberByNickname(nickname);
            if (!isPresent) {
                return USABLE_NICK;
            } else {
                return UNUSABLE_NICK;
            }
        } catch (Exception e) {
            System.err.println(e);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    public BaseResponseStatus modifyMemberInfo(MemberModifyReqDto reqDto, MultipartFile profileImg) throws BaseException {
        try {
            Member member = loginInfoService.getLoginMember();

            Disease disease = null;
            if (reqDto.getDiseaseId() != null) {
                disease = diseaseRepository.findById(reqDto.getDiseaseId())
                        .orElseThrow(() -> new BaseException(INVALID_DISEASE_ID));
            }

            String imgUrl = null;
            if (profileImg != null) {
                imgUrl = s3Uploader.upload(profileImg, "user");
            }

            member.modifyMember(reqDto, disease, imgUrl);
            return SUCCESS;
        } catch (BaseException e) {
            throw e;
        } catch (Exception e) {
            System.err.println(e);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    public MemberDetailResDto getMemberInfo(Long memberId, Pageable pageable) throws BaseException {
        try {
            if (loginInfoService.getLoginMemberId().equals(memberId)) {
                return getMyProfileInfo(memberId);
            }
            return getOtherProfileInfo(memberId, pageable);
        } catch (BaseException e) {
            throw e;
        } catch (Exception e) {
            System.err.println(e);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    private MemberDetailResDto getMyProfileInfo(Long memberId) throws BaseException {
        Member member = memberRepository.findMemberByIdFetchJoinDisease(memberId)
                .orElseThrow(() -> new BaseException(INVALID_MEMBER_ID));

        return new MemberDetailResDto(member);
    }

    private MemberDetailResDto getOtherProfileInfo(Long memberId, Pageable pageable) throws BaseException {
        Member member = memberRepository.findMemberByIdFetchJoinDisease(memberId)
                .orElseThrow(() -> new BaseException(INVALID_MEMBER_ID));
        Page<Post> posts = postRepository.findPostsByMember(member, pageable);

        return new MemberDetailResDto(member, posts);
    }
}