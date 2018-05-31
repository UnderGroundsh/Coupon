package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.lang.Exception.*;
import exceptions.DBErrorException;
import exceptions.InterruptedThreadException;


public class ConnectionPool {
    private Map<Connection, Boolean> connections;
    private static ConnectionPool ourInstance = new ConnectionPool();
    public static final String DBURL = "jdbc:derby://localhost:1527/CouponsDB;create=true";
	public static final String USER = "admin";
	public static final String PASSWORD = "admin";
	public static final int MAX_DB_CONNECTIONS = 5;

    public static ConnectionPool getInstance() {
        return ourInstance;
    }

    private ConnectionPool() {
    	connections = new HashMap<>(MAX_DB_CONNECTIONS);
    		for (int i = 0; i < MAX_DB_CONNECTIONS; i++) {
    			connections.put(createConnection(), true);
    		}
    }
    
    private Connection createConnection(){
    	try {
    		Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
			return DriverManager.getConnection(DBURL, USER, PASSWORD);
		} catch ( SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
    }

    public synchronized Connection getConnection() throws DBErrorException, InterruptedThreadException{
    	while (true) {
    		for (Map.Entry<Connection, Boolean> conn: connections.entrySet()) {
				if (conn.getValue()){
					try {
						if (conn.getKey().isClosed()){
							connections.remove(conn.getKey());
							Connection connection = createConnection();
							connections.put(connection, false);
							return connection;
						}
					} catch (SQLException e) {
						
						throw new DBErrorException();
					}
					conn.setValue(false);
	    			return conn.getKey();
				}
			}
			try {
				wait();
			
			} catch (InterruptedException e) {
				throw new InterruptedThreadException();
			}
    	}
    }

    public synchronized void returnConnection(Connection connection){
    	if (connection != null){
    		connections.put(connection, true);
    		notifyAll();
    	}
    }

    public void closeAllConnections() throws DBErrorException{
    	for (Connection conn : connections.keySet()) {
    		try {
				conn.close();
			} catch (SQLException e) {
			throw new DBErrorException();
			}
    	}
    	connections.clear();
    }
}
