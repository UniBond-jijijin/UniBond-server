package com.unibond.unibond.member.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.unibond.unibond.member.domain.Gender;
import com.unibond.unibond.member.domain.Member;
import com.unibond.unibond.post.domain.Post;
import com.unibond.unibond.post.dto.PostPreviewDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_NULL)
public class MemberDetailResDto {
    private String profileImage;
    private String nickname;
    private Gender gender;
    private String diseaseName;
    private LocalDate diagnosisTiming;
    private String bio;
    private List<String> interestList;

    private List<PostPreviewDto> postPreviewList;

    private Boolean lastPage;
    private Integer totalPages;
    private Long totalElements;
    private Integer size;

    // my profile
    public MemberDetailResDto(Member member) {
        this.profileImage = member.getProfileImage();
        this.nickname = member.getNickname();
        this.gender = member.getGender();
        this.diseaseName = member.getDisease().getDiseaseNameKor();
        this.diagnosisTiming = member.getDiagnosisTiming();
        this.bio = member.getBio();
        this.interestList = member.getInterestSet().stream().toList();
    }

    // other profile
    public MemberDetailResDto(Member member, Page<Post> postList) {
        this.profileImage = member.getProfileImage();
        this.nickname = member.getNickname();
        this.gender = member.getGender();
        this.diseaseName = member.getDisease().getDiseaseNameKor();
        this.diagnosisTiming = member.getDiagnosisTiming();
        this.bio = member.getBio();
        this.interestList = member.getInterestSet().stream().toList();

        this.postPreviewList = postList.getContent().stream().map(
                post -> new PostPreviewDto(member, member.getDisease(), post)
        ).collect(Collectors.toList());

        this.lastPage = postList.isLast();
        this.totalPages = postList.getTotalPages();
        this.totalElements = postList.getTotalElements();
        this.size = postList.getSize();
    }
}
