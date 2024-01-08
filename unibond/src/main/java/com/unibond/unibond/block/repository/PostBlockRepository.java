package com.unibond.unibond.block.repository;

import com.unibond.unibond.block.domain.PostBlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostBlockRepository extends JpaRepository<PostBlock, Long> {

}
