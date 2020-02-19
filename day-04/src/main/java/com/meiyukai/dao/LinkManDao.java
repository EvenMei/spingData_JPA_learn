package com.meiyukai.dao;

import com.meiyukai.domain.LinkMan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository(value = "linkManDao")
public interface LinkManDao extends JpaRepository<LinkMan , Long> , JpaSpecificationExecutor<LinkMan> {

}
