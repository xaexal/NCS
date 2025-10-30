package com.etoile.app.common;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class SshTunnel {
	private Session session;

    public void connectWithTunnel(String sshHost, String sshUser, String sshPassword,
                                   int sshPort, int localPort,
                                   String remoteHost, int remotePort) throws Exception {

        JSch jsch = new JSch();
        session = jsch.getSession(sshUser, sshHost, sshPort);
        session.setPassword(sshPassword);
        session.setConfig("StrictHostKeyChecking", "no"); // SSH 키 확인 비활성화 (보안 주의)

        session.setServerAliveInterval(60_000); // 60초마다 keepalive
        session.setServerAliveCountMax(10);     // 10번 실패 시 종료

        session.connect();
        System.out.println("✅ SSH 연결 성공");

        int assignedPort = session.setPortForwardingL(localPort, remoteHost, remotePort);
        System.out.println("✅ 포트 포워딩 설정됨: localhost:" + assignedPort + " → " + remoteHost + ":" + remotePort);
    }

    public void disconnect() {
        if (session != null && session.isConnected()) {
            session.disconnect();
            System.out.println("🔌 SSH 연결 종료");
        }
    }
}
