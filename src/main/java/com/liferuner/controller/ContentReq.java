package com.liferuner.controller;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContentReq {

	private List<BoardReq> imgs;
	private String html;
}
