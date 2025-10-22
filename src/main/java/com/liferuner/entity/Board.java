package com.liferuner.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "commu")
public class Board {
	
	@Id
	@GeneratedValue
	@Column(name = "b_idx")
	private Long idx;
	
	@Column(name = "b_category")
	private String category;
	
	@Column(name = "b_html")
	private String html;
	
	@Column(name = "b_path")
	private String path;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "user_id")
	private User user;
	
	public String getFilePath() {
		return "commu-images/" + path;
	}

	public String getImagePlusHtml() {
		String imageSrcl = html.replace("url|bind", getPath());
		return imageSrcl;
	}
}
