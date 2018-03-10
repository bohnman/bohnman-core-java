package com.github.bohnman.core.vcs;

import com.github.bohnman.core.lang.CoreStrings;

import java.io.InputStream;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Properties;

public class GitInfo {

    private static final DateTimeFormatter BUILD_DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ");

    private final ZonedDateTime buildDateTime;
    private final String buildAuthor;
    private final String abbreviatedCommitId;
    private final String commitId;
    private final String version;

    private GitInfo(String classpathResource) {
        Properties props = new Properties();
        String buildDate;
        String buildAuthor;
        String branch;
        String commitId;
        String abbreviatedCommitId;
        String tags;

        try {
            InputStream stream = GitInfo.class.getClassLoader().getResourceAsStream(classpathResource);

            if (stream != null) {
                props.load(stream);
            }

            buildAuthor = CoreStrings.defaultIfEmpty((String) props.get("git.build.user.name"), "");
            buildDate = CoreStrings.defaultIfEmpty((String) props.get("git.build.time"), "");
            tags = CoreStrings.defaultIfEmpty((String) props.get("git.tags"), "");
            branch = CoreStrings.defaultIfEmpty((String) props.get("git.branch"), "");
            abbreviatedCommitId = CoreStrings.defaultIfEmpty((String) props.get("git.commit.id.abbrev"), "");
            commitId = CoreStrings.defaultIfEmpty((String) props.get("git.commit.id"), "");
        } catch (Throwable e) {
            throw new RuntimeException(String.format("Error loading %s from the classpath.", classpathResource), e);

        }

        this.buildAuthor = buildAuthor;
        this.abbreviatedCommitId = abbreviatedCommitId;
        this.commitId = commitId;
        version = Objects.equals(commitId, branch) ? tags : "branch - " + branch;

        ZonedDateTime buildDateTime;

        try {
            buildDateTime = ZonedDateTime.parse(buildDate, BUILD_DATE_TIME_FORMAT);
        } catch (Exception eat) {
            buildDateTime = null;
        }

        this.buildDateTime = buildDateTime;
    }

    public String getBuildAuthor() {
        return buildAuthor;
    }

    public ZonedDateTime getBuildDateTime() {
        return buildDateTime;
    }

    public String getAbbreviatedCommitId() {
        return abbreviatedCommitId;
    }

    public String getCommitId() {
        return commitId;
    }

    public String getVersionText() {
        return version;
    }

    public static GitInfo forClasspathResource(String classpathResource) {
        return new GitInfo(classpathResource);
    }

    public static void main(String[] args) {
        GitInfo info = GitInfo.forClasspathResource("git.properties");
        System.out.println("Build Author: " + info.getBuildAuthor());
        System.out.println("Build Date: " + info.getBuildDateTime());
        System.out.println("Commit Id: " + info.getAbbreviatedCommitId());
        System.out.println("Version: " + info.getVersionText());
    }
}