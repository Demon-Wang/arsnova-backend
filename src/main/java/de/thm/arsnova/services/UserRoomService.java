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
package de.thm.arsnova.services;

import de.thm.arsnova.entities.UserAuthentication;
import de.thm.arsnova.entities.migration.v2.Room;

import java.util.UUID;

/**
 * The functionality the user-session service should provide.
 */
public interface UserRoomService {

	enum Role {
		STUDENT,
		SPEAKER
	}

	void setUser(UserAuthentication user);
	UserAuthentication getUser();

	void setRoom(Room room);
	Room getRoom();

	void setSocketId(UUID socketId);
	UUID getSocketId();

	void setRole(Role role);
	Role getRole();

	boolean inRoom();
	boolean isAuthenticated();
}