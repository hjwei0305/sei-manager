package com.changhong.sei.common;

import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.channel.ClientChannel;
import org.apache.sshd.client.channel.ClientChannelEvent;
import org.apache.sshd.client.session.ClientSession;
import org.apache.sshd.client.subsystem.sftp.SftpClient;
import org.apache.sshd.client.subsystem.sftp.SftpClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-12-11 08:18
 */
public class SshdExecutor {
    private static final Logger LOG = LoggerFactory.getLogger(SshdExecutor.class);

    private final SshClient client;
    private ClientSession session;

    private String result;
    private String error;

    public SshdExecutor(String ip, Integer port, String user) {
        client = SshClient.setUpDefaultClient();
        client.start();
        try {
            session = client.connect(user, ip, port).verify(10 * 1000).getSession();
        } catch (IOException e) {
            LOG.error("ssh会话创建异常", e);
        }
    }

    //密码方式
    public SshdExecutor(String ip, Integer port, String user, String password) {
        this(ip, port, user);
        session.addPasswordIdentity(password);
    }

    //公钥方式
    public SshdExecutor(String ip, Integer port, String user, String keyName, String publicKey) {
        this(ip, port, user);
//        try {
//            session.addPublicKeyIdentity(SecurityUtils.addPublicKeyIdentity(keyName, new ByteArrayInputStream(publicKey.getBytes()), null));
//            session.addPublicKeyIdentity(SecurityUtils.loadKeyPairIdentities(keyName, new ByteArrayInputStream(publicKey.getBytes()), null));
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (GeneralSecurityException e) {
//            e.printStackTrace();
//        }
    }

    //执行命令
    public void execute(String command) {
        try {
            if (!session.auth().verify(10 * 1000).isSuccess()) {
                throw new Exception("auth faild");
            }

            ClientChannel channel = session.createExecChannel(command);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ByteArrayOutputStream err = new ByteArrayOutputStream();
            channel.setOut(out);
            channel.setErr(err);

            if (!channel.open().verify(10 * 1000).isOpened()) {
                throw new Exception("open faild");
            }
            List<ClientChannelEvent> list = new ArrayList<>();
            list.add(ClientChannelEvent.CLOSED);
            channel.waitFor(list, 10 * 1000);
            channel.close();
            result = out.toString();
            error = err.toString();
        } catch (Exception e) {
            LOG.error("ssh执行命令[" + command + "]异常", e);
        } finally {
            if (client != null) {
                try {
                    client.stop();
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void uploadSFTP(String localPath, String remotePath) {
        try {
            if (!session.auth().verify(10 * 1000).isSuccess()) {
                throw new Exception("auth faild");
            }

            SftpClientFactory factory = SftpClientFactory.instance();
            SftpClient sftp = factory.createSftpClient(session);

            SftpClient.CloseableHandle handle = sftp.open(remotePath, EnumSet.of(SftpClient.OpenMode.Write, SftpClient.OpenMode.Create));
            try (FileInputStream in = new FileInputStream(localPath)) {
                int buffSize = 1024 * 1024;
                byte[] src = new byte[buffSize];
                int len;
                long fileOffset = 0L;
                while ((len = in.read(src)) != -1) {
                    sftp.write(handle, fileOffset, src, 0, len);
                    fileOffset += len;
                }
            } finally {
                sftp.close(handle);
            }
//            session.close(false);
            client.stop();
        } catch (Exception e) {
            LOG.error("ssh上传文件[" + localPath + "]异常", e);
        } finally {
            if (client != null) {
                try {
                    client.stop();
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getResult() {
        return result;
    }

    public String getError() {
        return error;
    }
}
