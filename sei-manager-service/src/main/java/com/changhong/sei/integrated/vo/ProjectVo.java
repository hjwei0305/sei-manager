package com.changhong.sei.integrated.vo;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 实现功能：
 *
 * @author 马超(Vision.Mac)
 * @version 1.0.00  2020-12-01 14:36
 */
public class ProjectVo implements Serializable {
    private static final long serialVersionUID = -8673090236429416613L;

    private String type;
    private String code;
    private String name;
    private String projectVersion;
    private String nameSpace;
    private String productNameSpace;
    private String productVersion;
    private String groupId;
    /**
     * gitId
     */
    private String gitId;
    /**
     * git地址
     */
    private String gitHttpUrl;
    private String gitSshUrl;
    private String gitWebUrl;
    private LocalDateTime gitCreateTime;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProjectVersion() {
        return projectVersion;
    }

    public void setProjectVersion(String projectVersion) {
        this.projectVersion = projectVersion;
    }

    public String getNameSpace() {
        return nameSpace;
    }

    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }

    public String getProductNameSpace() {
        return productNameSpace;
    }

    public void setProductNameSpace(String productNameSpace) {
        this.productNameSpace = productNameSpace;
    }

    public String getProductVersion() {
        return productVersion;
    }

    public void setProductVersion(String productVersion) {
        this.productVersion = productVersion;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGitId() {
        return gitId;
    }

    public void setGitId(String gitId) {
        this.gitId = gitId;
    }

    public String getGitHttpUrl() {
        return gitHttpUrl;
    }

    public void setGitHttpUrl(String gitHttpUrl) {
        this.gitHttpUrl = gitHttpUrl;
    }

    public String getGitSshUrl() {
        return gitSshUrl;
    }

    public void setGitSshUrl(String gitSshUrl) {
        this.gitSshUrl = gitSshUrl;
    }

    public String getGitWebUrl() {
        return gitWebUrl;
    }

    public void setGitWebUrl(String gitWebUrl) {
        this.gitWebUrl = gitWebUrl;
    }

    public LocalDateTime getGitCreateTime() {
        return gitCreateTime;
    }

    public void setGitCreateTime(LocalDateTime gitCreateTime) {
        this.gitCreateTime = gitCreateTime;
    }


    public static String str2Unicode(String string) {
        return str2Unicode(string, false);
    }

    /**
     * 字符串转 Unicode 编码
     *
     * @param string   原字符串
     * @param halfWith 是否转换半角字符
     * @return 编码后的字符串
     */
    public static String str2Unicode(String string, boolean halfWith) {
        if (string == null || string.isEmpty()) {
            // 传入字符串为空返回原内容
            return string;
        }

        StringBuilder value = new StringBuilder(string.length() << 3);
        String prefix = "\\u", zerofix = "0", unicode;
        char c;
        for (int i = 0, j; i < string.length(); i++) {
            c = string.charAt(i);
            if (!halfWith && c > 31 && c < 127) {
                // 不转换半角字符
                value.append(c);
                continue;
            }
            value.append(prefix);

            // 高 8 位
            j = c >>> 8;
            unicode = Integer.toHexString(j).toUpperCase();
            if (unicode.length() == 1) {
                value.append(zerofix);
            }
            value.append(unicode);

            // 低 8 位
            j = c & 0xFF;
            unicode = Integer.toHexString(j).toUpperCase();
            if (unicode.length() == 1) {
                value.append(zerofix);
            }
            value.append(unicode);
        }
        return value.toString();
    }

    public static void main(String[] args) {
        System.out.println(str2Unicode("财务共享运营API"));
    }
}
