package CS246_Group4.failsafe;

import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;

public class Hash {
    /*
     *   TO USE:
     *   In activity of use, paste the following:
     *       private Hash hasher = new Hash();
     *   and call getHash on each password to be hashed, and the parameter
     *   called "password" will be the User's password
     */

    protected String getHash(String password) {
        Hasher hasher = Hashing.sha256().newHasher();
        hasher.putString(password, Charsets.UTF_8);
        HashCode sha256 = hasher.hash();

        return sha256.toString();
    }
}
