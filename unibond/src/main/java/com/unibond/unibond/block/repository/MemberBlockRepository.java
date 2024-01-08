package com.unibond.unibond.block.repository;

import com.unibond.unibond.block.domain.MemberBlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberBlockRepository extends JpaRepository<MemberBlock, Long> {

}
