package com.example.chens.PlaymySong.server;

import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;

public class DatabaseControl {
    private final boolean DEBUG_MODE = false;

    public static String dbURL = "jdbc:mysql://localhost:3306/test?autoReconnect=true&useSSL=false";
    public static String userName = "root";
    public static String passWord = "1234";

    Properties props;


    /**
     * constructor
     * load properties file for mysql command
     */
    public DatabaseControl() {
        try {
            props= new Properties();
            FileInputStream in = new FileInputStream("sql_commands.properties");

            //load the command file in memory.
            props.load(in);
        } catch(Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * create a new database, and delete the old one
     */
    public void createDatabase() {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DriverManager.getConnection(dbURL,
                    userName, passWord);
            if (connection == null) {
                System.out.println("Can't connect to MySql server, end!");
                return;
            }
            statement = connection.createStatement();


            try {
                statement.executeUpdate(props.getProperty("create"));
                System.out.println("Database are created!");
            } catch (Exception e) {
                if (DEBUG_MODE) e.printStackTrace();
                System.out.println("The database already exists!");
            }

            try {
                statement.executeUpdate(props.getProperty("switch_to"));
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                statement.executeUpdate(props.getProperty("create_table"
                        + "_user_info"));
                System.out.println("Table user_info are created!");
            } catch (Exception e) {
                if (DEBUG_MODE) e.printStackTrace();
                System.out.println("Table user_info already exists!");
            }

            try {
                statement.executeUpdate(props.getProperty("create_table"
                        + "_favorite_list"));
                System.out.println("Table favorite_list are created!");
            } catch (Exception e) {
                if (DEBUG_MODE) e.printStackTrace();
                System.out.println("Table favorite_list already exists!");
            }

            try {
                statement.executeUpdate(props.getProperty("create_table"
                        + "_play_list"));
                System.out.println("Table play_list are created!");
            } catch (Exception e) {
                if (DEBUG_MODE) e.printStackTrace();
                System.out.println("Table play_list already exists!");
            }

            try {
                statement.executeUpdate(props.getProperty("create_table"
                        + "_wish_list"));
                System.out.println("Table wish_list are created!");
            } catch (Exception e) {
                if (DEBUG_MODE) e.printStackTrace();
                System.out.println("Table wish_list already exists!");
            }

            try {
                statement.executeUpdate(props.getProperty("create_table"
                        + "_feedback"));
                System.out.println("Table feedback are created!");
            } catch (Exception e) {
                if (DEBUG_MODE) e.printStackTrace();
                System.out.println("Table feedback already exists!");
            }

            try {
                statement.executeUpdate(props.getProperty("create_table"
                        + "_comment"));
                System.out.println("Table comment are created!");
            } catch (Exception e) {
                if (DEBUG_MODE) e.printStackTrace();
                System.out.println("Table comment already exists!");
            }

            try {
                statement.executeUpdate(props.getProperty("create_table"
                        + "_comment"
                        + "_content"));
                System.out.println("Table comment_content are created!");
            } catch (Exception e) {
                if (DEBUG_MODE) e.printStackTrace();
                System.out.println("Table comment_content already exists!");
            }

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.out.println(e);
                }
            }
        }
    }

    /**
     * used for login
     * @param id user id
     * @param passwd password
     * @return  0 - success
     *          1 - connection error
     *          2 - id not exists
     *          3 - wrong password
     *          4 - unknown error
     */
    public int login(String id, String passwd) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DriverManager.getConnection(dbURL,
                    userName, passWord);
            if (connection == null) {
                System.out.println("Can't connect to MySql server, end!");
                // connection error
                return 1;
            }
            statement = connection.createStatement();

            statement.executeUpdate(props.getProperty("switch_to"));
            String sql = props.getProperty("login");
            sql = sql.replaceFirst("!", id);
            ResultSet resultSet = statement.executeQuery(sql);

            if (!resultSet.next()) {
                // id not exists
                return 2;
            } else {
                String correctPasswd = resultSet.getString("passwd");
                if (passwd.equals(correctPasswd)) {
                    // correct id and password
                    return 0;
                } else {
                    // wrong pass word
                    return 3;
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
            return 4;
        }

    }

    /**
     * used for sign up
     * @param id user id
     * @param passwd password
     * @return  0 - success
     *          1 - connection error
     *          2 - id already exists, cannot be used for new user
     *          4 - unknown error
     */
    public int signUp(String id, String passwd) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DriverManager.getConnection(dbURL,
                    userName, passWord);
            if (connection == null) {
                System.out.println("Can't connect to MySql server, end!");
                // connection error
                return 1;
            }
            statement = connection.createStatement();

            statement.executeUpdate(props.getProperty("switch_to"));
            String sql = props.getProperty("login");
            sql = sql.replaceFirst("!", id);
            ResultSet resultSet = statement.executeQuery(sql);

            if (!resultSet.next()) {
                // id not exists, can be used for new user
                sql = props.getProperty("sign_up");
                sql = sql.replaceFirst("!", id);
                sql = sql.replaceFirst("!", passwd);
                statement.executeUpdate(sql);
                return 0;
            } else {
                // id already exists, cannot be used for new user
                return 2;
            }
        } catch(Exception e) {
            e.printStackTrace();
            return 4;
        }

    }

    /**
     * used for changing pass word
     * @param id user id
     * @param oldPasswd the old pass word
     * @param newPasswd the new pass word
     * @return  0 - success
     *          1 - connection error
     *          2 - id not exists
     *          3 - wrong password
     *          4 - unknown error
     */
    public int changePasswd(String id, String oldPasswd, String newPasswd) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DriverManager.getConnection(dbURL,
                    userName, passWord);
            if (connection == null) {
                System.out.println("Can't connect to MySql server, end!");
                // connection error
                return 1;
            }
            statement = connection.createStatement();

            statement.executeUpdate(props.getProperty("switch_to"));
            String sql = props.getProperty("login");
            sql = sql.replaceFirst("!", id);
            ResultSet resultSet = statement.executeQuery(sql);

            if (!resultSet.next()) {
                // id not exists
                return 2;
            } else {
                String correctPasswd = resultSet.getString("passwd");
                if (oldPasswd.equals(correctPasswd)) {
                    // correct id and password
                    sql = props.getProperty("change_passwd");
                    sql = sql.replaceFirst("!", newPasswd);
                    sql = sql.replaceFirst("!", id);
                    statement.executeUpdate(sql);

                    return 0;
                } else {
                    // wrong pass word
                    return 3;
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
            return 4;
        }

    }

    /**
     * used for adding songs to favorite list
     * @param id user id
     * @param titleSinger the song's title and singer
     * @param album the album name
     * @return  0 - success
     *          1 - connection error
     *          4 - unknown error
     */
    public int addFavoriteList(String id, String titleSinger, String album) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DriverManager.getConnection(dbURL, userName, passWord);
            if (connection == null) {
                System.out.println("Can't connect to MySql server, end!");
                // connection error
                return 1;
            }
            statement = connection.createStatement();
        } catch(Exception e) {
            e.printStackTrace();
            return 4;
        }

        try {
            statement.executeUpdate(props.getProperty("switch_to"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String sql = props.getProperty("add_favorite_list");
        sql = sql.replaceFirst("!", titleSinger);
        sql = sql.replaceFirst("!", album);

        try {
            statement.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }

        sql = props.getProperty("get_user_favorite_list");
        sql = sql.replaceFirst("!", id);

        try {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                String favList = resultSet.getString("favorite_list");
                if (favList.equals("")) {
                    favList += titleSinger;
                } else {
                    favList += ",";
                    favList += titleSinger;
                }
                sql = props.getProperty("update_user_favorite_list");
                sql = sql.replaceFirst("!", favList);
                sql = sql.replaceFirst("!", id);
                statement.executeUpdate(sql);
            }
            return 0;
        } catch(Exception e) {
            e.printStackTrace();
            return 4;
        }

    }

    /**
     * used for deleting songs in favorite list from user profile
     * @param id user id
     * @param titleSinger the song's title and singer
     * @return  0 - success
     *          1 - connection error
     *          4 - unknown error
     */
    public int deleteFavoriteList(String id, String titleSinger) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DriverManager.getConnection(dbURL, userName, passWord);
            if (connection == null) {
                System.out.println("Can't connect to MySql server, end!");
                // connection error
                return 1;
            }
            statement = connection.createStatement();
        } catch(Exception e) {
            e.printStackTrace();
            return 4;
        }

        try {
            statement.executeUpdate(props.getProperty("switch_to"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String sql = props.getProperty("get_user_favorite_list");
        sql = sql.replaceFirst("!", id);

        try {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                String favList = resultSet.getString("favorite_list");
                favList = favList.replace(titleSinger, "");
                favList = favList.replace(",,", ",");
                favList = favList.replaceFirst("^,", "");

                sql = props.getProperty("update_user_favorite_list");
                sql = sql.replaceFirst("!", favList);
                sql = sql.replaceFirst("!", id);
                statement.executeUpdate(sql);
            }
            return 0;
        } catch(Exception e) {
            e.printStackTrace();
            return 4;
        }

    }


    /**
     * used for adding songs to play list
     * @param id user id
     * @param titleSinger the song's title and singer
     * @param album the album name
     * @return  0 - success
     *          1 - connection error
     *          4 - unknown error
     */
    public int addPlayList(String id, String titleSinger, String album) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DriverManager.getConnection(dbURL, userName, passWord);
            if (connection == null) {
                System.out.println("Can't connect to MySql server, end!");
                // connection error
                return 1;
            }
            statement = connection.createStatement();
        } catch(Exception e) {
            e.printStackTrace();
            return 4;
        }

        try {
            statement.executeUpdate(props.getProperty("switch_to"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String sql = props.getProperty("add_play_list");
        sql = sql.replaceFirst("!", titleSinger);
        sql = sql.replaceFirst("!", album);

        try {
            statement.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }

        sql = props.getProperty("get_user_play_list");
        sql = sql.replaceFirst("!", id);

        try {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                String favList = resultSet.getString("play_list");
                if (favList.equals("")) {
                    favList += titleSinger;
                } else {
                    favList += ",";
                    favList += titleSinger;
                }
                sql = props.getProperty("update_user_play_list");
                sql = sql.replaceFirst("!", favList);
                sql = sql.replaceFirst("!", id);
                statement.executeUpdate(sql);
            }
            return 0;
        } catch(Exception e) {
            e.printStackTrace();
            return 4;
        }

    }


    /**
     * used for deleting songs in play list from user profile
     * @param id user id
     * @param titleSinger the song's title and singer
     * @return  0 - success
     *          1 - connection error
     *          4 - unknown error
     */
    public int deletePlayList(String id, String titleSinger) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DriverManager.getConnection(dbURL, userName, passWord);
            if (connection == null) {
                System.out.println("Can't connect to MySql server, end!");
                // connection error
                return 1;
            }
            statement = connection.createStatement();
        } catch(Exception e) {
            e.printStackTrace();
            return 4;
        }

        try {
            statement.executeUpdate(props.getProperty("switch_to"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String sql = props.getProperty("get_user_play_list");
        sql = sql.replaceFirst("!", id);

        try {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                String favList = resultSet.getString("play_list");
                favList = favList.replace(titleSinger, "");
                favList = favList.replace(",,", ",");
                favList = favList.replaceFirst("^,", "");

                sql = props.getProperty("update_user_play_list");
                sql = sql.replaceFirst("!", favList);
                sql = sql.replaceFirst("!", id);
                statement.executeUpdate(sql);
            }
            return 0;
        } catch(Exception e) {
            e.printStackTrace();
            return 4;
        }

    }


    /**
     * used for adding songs to wish list
     * @param id user id
     * @param titleSinger the song's title and singer
     * @param album the album name
     * @param link song's web site url
     * @return  0 - success
     *          1 - connection error
     *          4 - unknown error
     */
    public int addWishList(String id, String titleSinger, String album,
                           String link) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DriverManager.getConnection(dbURL, userName, passWord);
            if (connection == null) {
                System.out.println("Can't connect to MySql server, end!");
                // connection error
                return 1;
            }
            statement = connection.createStatement();
        } catch(Exception e) {
            e.printStackTrace();
            return 4;
        }

        try {
            statement.executeUpdate(props.getProperty("switch_to"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String sql = props.getProperty("add_wish_list");
        sql = sql.replaceFirst("!", titleSinger);
        sql = sql.replaceFirst("!", album);
        sql = sql.replaceFirst("!", link);

        try {
            statement.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }

        sql = props.getProperty("get_user_wish_list");
        sql = sql.replaceFirst("!", id);

        try {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                String favList = resultSet.getString("wish_list");
                if (favList.equals("")) {
                    favList += titleSinger;
                } else {
                    favList += ",";
                    favList += titleSinger;
                }
                sql = props.getProperty("update_user_wish_list");
                sql = sql.replaceFirst("!", favList);
                sql = sql.replaceFirst("!", id);
                statement.executeUpdate(sql);
            }
            return 0;
        } catch(Exception e) {
            e.printStackTrace();
            return 4;
        }

    }


    /**
     * used for deleting songs in wish list from user profile
     * @param id user id
     * @param titleSinger the song's title and singer
     * @return  0 - success
     *          1 - connection error
     *          4 - unknown error
     */
    public int deleteWishList(String id, String titleSinger) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DriverManager.getConnection(dbURL, userName, passWord);
            if (connection == null) {
                System.out.println("Can't connect to MySql server, end!");
                // connection error
                return 1;
            }
            statement = connection.createStatement();
        } catch(Exception e) {
            e.printStackTrace();
            return 4;
        }

        try {
            statement.executeUpdate(props.getProperty("switch_to"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String sql = props.getProperty("get_user_wish_list");
        sql = sql.replaceFirst("!", id);

        try {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                String favList = resultSet.getString("wish_list");
                favList = favList.replace(titleSinger, "");
                favList = favList.replace(",,", ",");
                favList = favList.replaceFirst("^,", "");
                sql = props.getProperty("update_user_wish_list");
                sql = sql.replaceFirst("!", favList);
                sql = sql.replaceFirst("!", id);
                statement.executeUpdate(sql);
            }
            return 0;
        } catch(Exception e) {
            e.printStackTrace();
            return 4;
        }

    }

    /**
     * used for adding feedback
     * @param userIdTime the user id and send time
     * @param content the feedback content
     * @return  0 - success
     *          1 - connection error
     *          4 - unknown error
     */
    public int sendFeedback(String userIdTime, String content) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DriverManager.getConnection(dbURL, userName, passWord);
            if (connection == null) {
                System.out.println("Can't connect to MySql server, end!");
                // connection error
                return 1;
            }
            statement = connection.createStatement();
        } catch(Exception e) {
            e.printStackTrace();
            return 4;
        }

        try {
            statement.executeUpdate(props.getProperty("switch_to"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String sql = props.getProperty("add_feedback");
        sql = sql.replaceFirst("!", userIdTime);
        sql = sql.replaceFirst("!", content);

        try {
            statement.executeUpdate(sql);
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 4;
        }

    }


    /**
     * used for adding comment
     * @param id Time the user id and send time
     * @param content the feedback content
     * @return  0 - success
     *          1 - connection error
     *          4 - unknown error
     */
    public int sendComment(String titleSinger, String id, String time,
                           String content, String score) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DriverManager.getConnection(dbURL, userName, passWord);
            if (connection == null) {
                System.out.println("Can't connect to MySql server, end!");
                // connection error
                return 1;
            }
            statement = connection.createStatement();
        } catch(Exception e) {
            e.printStackTrace();
            return 4;
        }

        try {
            statement.executeUpdate(props.getProperty("switch_to"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String sql = props.getProperty("add_comment");
        sql = sql.replaceFirst("!", titleSinger);
        sql = sql.replaceFirst("!", id);

        try {
            statement.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
            return 4;
        }

        sql = props.getProperty("add_comment_content");
        sql = sql.replaceFirst("!", titleSinger + "*" + id);
        sql = sql.replaceFirst("!", time);
        sql = sql.replaceFirst("!", content);
        sql = sql.replaceFirst("!", score);

        try {
            statement.executeUpdate(sql);
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 4;
        }

    }



    /**
     * used for updating user info
     * @param id user id
     * @param passwd pass word
     * @param email user's email address
     * @param photo user's profile photo
     * @param whatsUp user's notes
     * @return  0 - success
     *          1 - connection error
     *          4 - unknown error
     */
    public int updateUserInfo(String id, String passwd, String email,
                              String photo, String whatsUp) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DriverManager.getConnection(dbURL, userName, passWord);
            if (connection == null) {
                System.out.println("Can't connect to MySql server, end!");
                // connection error
                return 1;
            }
            statement = connection.createStatement();
        } catch(Exception e) {
            e.printStackTrace();
            return 4;
        }

        try {
            statement.executeUpdate(props.getProperty("switch_to"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String sql = props.getProperty("update_user_info");
        sql = sql.replaceFirst("!", passwd);
        sql = sql.replaceFirst("!", email);
        sql = sql.replaceFirst("!", photo);
        sql = sql.replaceFirst("!", whatsUp);
        sql = sql.replaceFirst("!", id);

        try {
            statement.executeUpdate(sql);
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 4;
        }

    }

    /**
     * used for getting user info, columns are separated by "*;*"
     *
     * @param id user id
     * @return  user profile information
     *          1 - connection error
     *          4 - unknown error
     *
     */
    public String getUserInfo(String id) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DriverManager.getConnection(dbURL, userName, passWord);
            if (connection == null) {
                System.out.println("Can't connect to MySql server, end!");
                // connection error
                return "1";
            }
            statement = connection.createStatement();
        } catch(Exception e) {
            e.printStackTrace();
            return "4";
        }

        try {
            statement.executeUpdate(props.getProperty("switch_to"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String sql = props.getProperty("get_user_info");
        sql = sql.replaceFirst("!", id);

        try {
            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.next();
            StringBuilder result = new StringBuilder("");

            result.append(resultSet.getString("passwd"));
            result.append("*;*");
            result.append(resultSet.getString("email"));
            result.append("*;*");
            result.append(resultSet.getString("photo"));
            result.append("*;*");
            result.append(resultSet.getString("wahts_up"));
            result.append("*;*");
            result.append(resultSet.getString("favorite_list"));
            result.append("*;*");
            result.append(resultSet.getString("wish_list"));
            result.append("*;*");
            result.append(resultSet.getString("play_list"));
            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "4";
        }

    }

    /**
     * used for getting comment, comments are separated by ",;,", columns
     * are separated by "*;*"
     *
     * @param titleSinger user id
     * @return  user comment
     *          1 - connection error
     *          4 - unknown error
     *
     */
    public String getComment(String titleSinger) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DriverManager.getConnection(dbURL, userName, passWord);
            if (connection == null) {
                System.out.println("Can't connect to MySql server, end!");
                // connection error
                return "1";
            }
            statement = connection.createStatement();
        } catch(Exception e) {
            e.printStackTrace();
            return "4";
        }

        try {
            statement.executeUpdate(props.getProperty("switch_to"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String sql = props.getProperty("get_comment_users");
        sql = sql.replaceFirst("!", titleSinger);

        String users = null;
        try {
            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.next();
            users = resultSet.getString("id");


        } catch (Exception e) {
            e.printStackTrace();
            return "4";
        }

        if (users == null) {
            return "";
        } else {
            String[] usersArray = users.split(",");
            StringBuilder result = new StringBuilder("");
            for (int i = 0; i < usersArray.length; i++) {
                String titleSingerId = titleSinger + " - " + usersArray[i];
                sql = props.getProperty("get_comment");
                sql = sql.replaceFirst("!", titleSingerId);
                try {
                    ResultSet resultSet = statement.executeQuery(sql);
                    resultSet.next();

                    result.append(resultSet.getString("time"));
                    result.append("*;*");
                    result.append(resultSet.getString("content"));
                    result.append("*;*");
                    result.append(resultSet.getString("score"));
                    if (i != usersArray.length - 1) {
                        result.append(",;,");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return "4";
                }
            }
            return result.toString();
        }

    }


    /**
     * used for getting music from favorite list, columns are separated by "*;*"
     *
     * @param titleSinger title and singer
     * @return favorite music info
     *          1 - connection error
     *          4 - unknown error
     *
     */
    public String getFavoriteMusic(String titleSinger) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DriverManager.getConnection(dbURL, userName, passWord);
            if (connection == null) {
                System.out.println("Can't connect to MySql server, end!");
                // connection error
                return "1";
            }
            statement = connection.createStatement();
        } catch(Exception e) {
            e.printStackTrace();
            return "4";
        }

        try {
            statement.executeUpdate(props.getProperty("switch_to"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String sql = props.getProperty("get_favorite_music");
        sql = sql.replaceFirst("!", titleSinger);

        try {
            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.next();
            StringBuilder result = new StringBuilder("");

            result.append(resultSet.getString("title_singer"));
            result.append("*;*");
            result.append(resultSet.getString("album"));

            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "4";
        }

    }

    /**
     * used for getting music from play list, columns are separated by "*;*"
     *
     * @param titleSinger title and singer
     * @return play music info
     *          1 - connection error
     *          4 - unknown error
     *
     */
    public String getPlayMusic(String titleSinger) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DriverManager.getConnection(dbURL, userName, passWord);
            if (connection == null) {
                System.out.println("Can't connect to MySql server, end!");
                // connection error
                return "1";
            }
            statement = connection.createStatement();
        } catch(Exception e) {
            e.printStackTrace();
            return "4";
        }

        try {
            statement.executeUpdate(props.getProperty("switch_to"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String sql = props.getProperty("get_play_music");
        sql = sql.replaceFirst("!", titleSinger);

        try {
            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.next();
            StringBuilder result = new StringBuilder("");

            result.append(resultSet.getString("title_singer"));
            result.append("*;*");
            result.append(resultSet.getString("album"));

            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "4";
        }

    }

    /**
     * used for getting music from wish list, columns are separated by "*;*"
     *
     * @param titleSinger title and singer
     * @return wish music info
     *          1 - connection error
     *          4 - unknown error
     *
     */
    public String getWishMusic(String titleSinger) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DriverManager.getConnection(dbURL, userName, passWord);
            if (connection == null) {
                System.out.println("Can't connect to MySql server, end!");
                // connection error
                return "1";
            }
            statement = connection.createStatement();
        } catch(Exception e) {
            e.printStackTrace();
            return "4";
        }

        try {
            statement.executeUpdate(props.getProperty("switch_to"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String sql = props.getProperty("get_wish_music");
        sql = sql.replaceFirst("!", titleSinger);

        try {
            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.next();
            StringBuilder result = new StringBuilder("");

            result.append(resultSet.getString("title_singer"));
            result.append("*;*");
            result.append(resultSet.getString("album"));
            result.append("*;*");
            result.append(resultSet.getString("link"));

            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "4";
        }

    }
}
