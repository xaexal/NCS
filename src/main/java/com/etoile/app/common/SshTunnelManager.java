package com.etoile.app.common;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Component
public class SshTunnelManager {
	private final SshTunnel sshTunnel = new SshTunnel();

    @PostConstruct
    public void init() throws Exception {
        sshTunnel.connectWithTunnel(
                "193.123.234.59",    // SSH 서버 주소
                "ubuntu",          // SSH 사용자
                "wogud99#",      // SSH 비밀번호
                22,                  // SSH 포트 (기본: 22)
                3307,                // 로컬 포트 (임의 설정)
                "127.0.0.1",         // 원격 DB 주소
                3306                 // 원격 DB 포트 (MySQL 예시)
        );
    }

    @PreDestroy
    public void shutdown() {
        sshTunnel.disconnect();
    }
}
