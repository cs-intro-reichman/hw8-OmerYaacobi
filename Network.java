

/** Represents a social network. The network has users, who follow other uesrs.
 *  Each user is an instance of the User class. */
public class Network {

    // Fields
    private User[] users;  // the users in this network (an array of User objects)
    private int userCount; // actual number of users in this network

    /** Creates a network with a given maximum number of users. */
    public Network(int maxUserCount) {
        this.users = new User[maxUserCount];
        this.userCount = 0;
    }

    /** Creates a network  with some users. The only purpose of this constructor is 
     *  to allow testing the toString and getUser methods, before implementing other methods. */
    public Network(int maxUserCount, boolean gettingStarted) {
        this(maxUserCount);
        users[0] = new User("Foo");
        users[1] = new User("Bar");
        users[2] = new User("Baz");
        userCount = 3;
    }

    public int getUserCount() {
        return this.userCount;
    }
    /** Finds in this network, and returns, the user that has the given name.
     *  If there is no such user, returns null.
     *  Notice that the method receives a String, and returns a User object. */
    public User getUser(String name) {
        if (name.length() == 0) {
            return null;
        }
        if (userCount == 0) {
            return null;
        }
        for (int i = 0; i < userCount; i++) {
            String userName = this.users[i].getName();
            if (userName.equals(name)) {
                return this.users[i];
            }
        }
        return null;
    }

    /** Adds a new user with the given name to this network.
    *  If ths network is full, does nothing and returns false;
    *  If the given name is already a user in this network, does nothing and returns false;
    *  Otherwise, creates a new user with the given name, adds the user to this network, and returns true. */
    public boolean addUser(String name) {
       
       
       if (this.userCount < users.length) {
            if (! isAUser(name)) {
                if (name.length() != 0) {
                    users[userCount] = new User (name);
                    userCount ++;
                    return true;
                }
            }
       }
        return false;
    }

    /** Makes the user with name1 follow the user with name2. If successful, returns true.
     *  If any of the two names is not a user in this network,
     *  or if the "follows" addition failed for some reason, returns false. */
    public boolean addFollowee(String name1, String name2) {
        if (name1.equals(name2)) {
            return false;
        }
       if (isAUser(name1) && isAUser(name2)) {
        this.getUser(name1).addFollowee(name2);
        return true;
       }
       
        return false;
    }
    
    /** For the user with the given name, recommends another user to follow. The recommended user is
     *  the user that has the maximal mutual number of followees as the user with the given name. */
    public String recommendWhoToFollow(String name) {
        String mostRecommendedUserToFollow = "";
        int index = indexOf(name);
        int maxInCommon = 0;
        if (index == -1) {
            return mostRecommendedUserToFollow;
        }
        for (int i = 0; i < this.userCount; i++) {
            if (i == index) {
                continue;
            }
            int mutual = getUser(name).countMutual(users[i]);
            if (mutual == maxInCommon) {
                mostRecommendedUserToFollow += ", " + users [i].getName();
            }
            if (mutual > maxInCommon) {
                maxInCommon = mutual;
                mostRecommendedUserToFollow = users[i].getName();
            }
            
        }

        return mostRecommendedUserToFollow;
    }

    /** Computes and returns the name of the most popular user in this network: 
     *  The user who appears the most in the follow lists of all the users. */
    public String mostPopularUser() {
        String mostPopularUser = users[0].getName();
        int mostFollowers = followeeCount(users[0].getName());
        // I will compare the second user to thr first user' and after that to 
        // the one go is followed by more users.

        for (int i = 1; i < this.userCount; i++) {
            int followedBy = followeeCount(users[i].getName());
            // Incase there are few different users with the most followees.
            if (followedBy == mostFollowers) {
                mostPopularUser += ", " + users[i].getName();
            }
            
            if (followedBy > mostFollowers) {
                mostFollowers = followedBy;
                mostPopularUser = users[i].getName();
            }
        }
        return mostPopularUser;
    }

    /** Returns the number of times that the given name appears in the follows lists of all
     *  the users in this network. Note: A name can appear 0 or 1 times in each list. */
    private int followeeCount(String name) {
        int index = indexOf(name);
        int [] followedBy = new int[this.userCount];
        for (int i = 0; i < this.userCount; i++) {
            for (int j = 0; j < this.userCount; j++) {
                if (users[i].follows(users[j].getName())) {
                    followedBy[j] ++;
                }
            }
        }
        return followedBy[index];
    }

    // Returns a textual description of all the users in this network, and who they follow.
    public String toString() {
    String str = "Network:";
    for (int i = 0; i < this.userCount; i++) {
        str = str + "\n" + this.users[i];
    }
       return str;
    }
    
    // Checks if a name is in the network
    public boolean isAUser (String name) {
        if (getUser(name) != null) {
            return true;
        } 
        return false;
    }
    // Getiing the index of each user
    public int indexOf (String name) {
        int index = -1;
        for (int i = 0; i < this.userCount; i++) { // Finding the index of this.user
           String iName = users[i].getName();
            if (iName.equals(name)) {
                index = i;
            }
        }
        return index;
    }
}
