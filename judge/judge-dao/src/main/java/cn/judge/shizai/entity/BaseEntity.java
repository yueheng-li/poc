package cn.judge.shizai.entity;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class BaseEntity {

	public String toString() {
		return new ReflectionToStringBuilder(this).toString();
	}
}
