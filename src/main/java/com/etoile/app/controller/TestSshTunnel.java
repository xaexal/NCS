package com.etoile.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etoile.app.common.SshTunnelManager;


@RestController
public class TestSshTunnel {
	private final SshTunnelManager tunnelManager;

    // 생성자 주입
    public TestSshTunnel(SshTunnelManager tunnelManager) {
        this.tunnelManager = tunnelManager;
    }

    @GetMapping("/test-ssh")
    public String testSsh() {
        return "SSH 터널이 이미 설정되어 있어요!";
    }
}
