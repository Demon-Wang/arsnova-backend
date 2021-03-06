/*
 * This file is part of ARSnova Backend.
 * Copyright (C) 2012-2017 The ARSnova Team
 *
 * ARSnova Backend is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * ARSnova Backend is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.thm.arsnova.entities;

import com.fasterxml.jackson.annotation.JsonView;
import de.thm.arsnova.entities.serialization.View;

/**
 * A login service description. For example, this class is used to display the login buttons in ARSnova mobile.
 */
public class ServiceDescription {
	private String id;
	private String name;
	private String dialogUrl;
	private String image;
	private int order = 0;
	private String[] allowedRoles;

	public ServiceDescription(String id, String name, String dialogUrl) {
		this.id = id;
		this.name = name;
		this.dialogUrl = dialogUrl;
	}

	public ServiceDescription(String id, String name, String dialogUrl, String[] allowedRoles) {
		this.id = id;
		this.name = name;
		this.dialogUrl = dialogUrl;
		this.allowedRoles = allowedRoles;
	}

	public ServiceDescription(String id, String name, String dialogUrl, String[] allowedRoles, String image) {
		this.id = id;
		this.name = name;
		this.dialogUrl = dialogUrl;
		this.allowedRoles = allowedRoles;
		if (!"".equals(image)) {
			this.image = image;
		}
	}

	@JsonView(View.Public.class)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@JsonView(View.Public.class)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonView(View.Public.class)
	public String getDialogUrl() {
		return dialogUrl;
	}

	public void setDialogUrl(String dialogUrl) {
		this.dialogUrl = dialogUrl;
	}

	@JsonView(View.Public.class)
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@JsonView(View.Public.class)
	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	@JsonView(View.Public.class)
	public String[] getAllowedRoles() {
		return allowedRoles;
	}

	public void setAllowedRoles(String[] roles) {
		this.allowedRoles = roles;
	}
}
