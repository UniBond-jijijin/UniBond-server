package com.unibond.unibond.letter_room.dto;

import com.unibond.unibond.common.PageInfo;
import com.unibond.unibond.letter_room.repository.repo_interface.LetterRoomPreviewRepoInterface;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetAllLetterRoomsResDto {
    private List<LetterRoomPreviewResDto> letterRoomList;
    private PageInfo pageInfo;

    public GetAllLetterRoomsResDto(Long loginId, Page<LetterRoomPreviewRepoInterface> letterRoomList) {
        this.pageInfo = new PageInfo(letterRoomList);
        this.letterRoomList = letterRoomList.stream().map(
                letterRoomPreviewRepoInterface -> new LetterRoomPreviewResDto(loginId, letterRoomPreviewRepoInterface)
        ).collect(Collectors.toList());
    }

}
