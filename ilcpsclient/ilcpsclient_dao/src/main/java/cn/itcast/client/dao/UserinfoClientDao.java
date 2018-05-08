package cn.itcast.client.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.itcast.client.domain.UserinfoClient;

public interface UserinfoClientDao extends JpaRepository<UserinfoClient, String> {
	
}
