package de.thm.arsnova.persistance.couchdb.migrations;

import de.thm.arsnova.entities.Room;
import de.thm.arsnova.entities.UserProfile;
import de.thm.arsnova.entities.migration.FromV2Migrator;
import de.thm.arsnova.entities.migration.v2.DbUser;
import de.thm.arsnova.entities.migration.v2.LoggedIn;
import de.thm.arsnova.entities.migration.v2.MotdList;
import de.thm.arsnova.persistance.UserRepository;
import de.thm.arsnova.persistance.couchdb.support.MangoCouchDbConnector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class V2ToV3Migration implements Migration {
	private static final String ID = "20170914131300";
	private static final int LIMIT = 200;

	private FromV2Migrator migrator;
	private MangoCouchDbConnector connector;
	private UserRepository userRepository;

	public V2ToV3Migration(
			final FromV2Migrator migrator,
			final MangoCouchDbConnector connector,
			final UserRepository userRepository) {
		this.migrator = migrator;
		this.connector = connector;
		this.userRepository = userRepository;
	}

	public String getId() {
		return ID;
	}

	public void migrate() {
		migrateUsers();
		migrateRooms();
	}

	private void migrateUsers() {
		HashMap<String, Object> queryOptions = new HashMap<>();
		queryOptions.put("type", "user");
		MangoCouchDbConnector.MangoQuery query = connector.new MangoQuery(queryOptions);
		query.setLimit(LIMIT);

		for (int skip = 0;; skip += LIMIT) {
			List<UserProfile> profilesV3 = new ArrayList<>();
			List<DbUser> dbUsersV2 = connector.query(query, DbUser.class);
			if (dbUsersV2.size() == 0) {
				break;
			}

			for (DbUser userV2 : dbUsersV2) {
				HashMap<String, Object> loggedInQueryOptions = new HashMap<>();
				loggedInQueryOptions.put("type", "logged_in");
				loggedInQueryOptions.put("user", userV2.getUsername());
				MangoCouchDbConnector.MangoQuery loggedInQuery = connector.new MangoQuery(loggedInQueryOptions);
				List<LoggedIn> loggedInList = connector.query(loggedInQuery, LoggedIn.class);
				LoggedIn loggedIn = loggedInList.size() > 0 ? loggedInList.get(0) : null;

				HashMap<String, Object> motdListQueryOptions = new HashMap<>();
				motdListQueryOptions.put("type", "motdlist");
				motdListQueryOptions.put("username", userV2.getUsername());
				MangoCouchDbConnector.MangoQuery motdlistQuery = connector.new MangoQuery(motdListQueryOptions);
				List<MotdList> motdListList = connector.query(motdlistQuery, MotdList.class);
				MotdList motdList = motdListList.size() > 0 ? motdListList.get(0) : null;

				profilesV3.add(migrator.migrate(userV2, loggedIn, motdList));
			}

			connector.executeBulk(profilesV3);
		}
	}

	private void migrateRooms() {
		HashMap<String, Object> queryOptions = new HashMap<>();
		queryOptions.put("type", "session");
		MangoCouchDbConnector.MangoQuery query = connector.new MangoQuery(queryOptions);
		query.setLimit(LIMIT);

		for (int skip = 0;; skip += LIMIT) {
			List<Room> roomsV3 = new ArrayList<>();
			List<de.thm.arsnova.entities.migration.v2.Room> roomsV2 = connector.query(query,
					de.thm.arsnova.entities.migration.v2.Room.class);

			for (de.thm.arsnova.entities.migration.v2.Room roomV2 : roomsV2) {
				UserProfile profile = userRepository.findByUsername(roomV2.getCreator());
				roomsV3.add(migrator.migrate(roomV2, profile));
			}

			connector.executeBulk(roomsV3);
		}
	}
}
