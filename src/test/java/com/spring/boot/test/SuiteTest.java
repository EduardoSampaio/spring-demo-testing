package com.spring.boot.test;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SelectPackages({
	"com.spring.boot.test.repositories",
	"com.spring.boot.test.services",
	"com.spring.boot.test.resources",
	"com.spring.boot.test.integration"})
public class SuiteTest {}
