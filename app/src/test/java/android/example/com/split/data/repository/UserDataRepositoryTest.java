package android.example.com.split.data.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserDataRepositoryTest {

  UserDataRepository repository;
  String userId;

  @Before
  public void setUp() throws Exception {
    /*FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    userId = auth.getCurrentUser().getUid();*/

    repository = new UserDataRepository();
  }

  @After
  public void tearDown() throws Exception {
    repository = null;
  }

  @Test
  public void createNewUser() {
   /* User user = new User();
    // user.setId("TESTID");
    repository.createNewUser(user, userId, new UserDataRepository.OnUserCreated() {
      @Override
      public void onUserCreated(Boolean userCreated) {
        if (userCreated) {
          assertTrue(true);
        } else {
          assertTrue(false);
        }
      }
    });*/
  }

  @Test
  public void addNewContact() {
  }

  @Test
  public void getDocumentId() {
  }

  @Test
  public void isUserExist() {
  }

  @Test
  public void addNewContact1() {
  }

  @Test
  public void getContactlist() {
  }

  @Test
  public void getUserDetail() {
  }
}