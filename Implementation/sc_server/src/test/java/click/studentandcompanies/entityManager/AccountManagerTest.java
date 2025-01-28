package click.studentandcompanies.entityManager;

import click.studentandcompanies.entity.*;
import click.studentandcompanies.entityManager.accountManager.AccountManager;
import click.studentandcompanies.entityRepository.*;
import click.studentandcompanies.utils.UserType;
import click.studentandcompanies.utils.exception.BadInputException;
import click.studentandcompanies.utils.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Condensed style test for AccountManager with one test method per public method
 * covering multiple success and error scenarios for high coverage.
 */
class AccountManagerTest extends EntityFactory {

    @Mock
    private UniversityRepository universityRepository;
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private CompanyRepository companyRepository;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private UserManager userManager;

    @InjectMocks
    private AccountManager accountManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendUserData() throws BadInputException {
        // Setup common mocks
        List<Account> existingAccounts = new ArrayList<>();
        when(accountRepository.findAll()).thenReturn(existingAccounts);

        // Base payload (valid for a STUDENT scenario)
        Map<String, Object> payload = new HashMap<>();
        payload.put("user_id", "UUID-123");
        payload.put("userType", "STUDENT");
        payload.put("name", "Alice");
        payload.put("email", "alice@example.com");
        payload.put("country", "IT");
        payload.put("surname", "Smith");
        payload.put("uniVat", 1234);
        payload.put("birthDate", LocalDate.now().toString());

        Account acc = new Account("UUID-123", "Alice", "alice@example.com", "IT",
                UserType.STUDENT, false, "Smith", 1234, LocalDate.now(), null, null, null);
        when(accountRepository.save(any(Account.class))).thenReturn(acc);
        University uni = setNewUniversity(1,"MIT", "IT");
        uni.setVatNumber(1234);
        when(universityRepository.findAll()).thenReturn(List.of(uni));

        // --- Success for STUDENT ---
        Account result = accountManager.sendUserData(payload);
        assertNotNull(result);
        assertEquals("UUID-123", result.getUserID());
        verify(accountRepository).save(any(Account.class));

        // --- Email already in use ---
        existingAccounts.add(acc);  // "alice@example.com" is now taken
        Map<String, Object> secondPayload = new HashMap<>(payload);
        secondPayload.put("user_id", "UUID-999");
        assertThrows(BadInputException.class, () ->
                accountManager.sendUserData(secondPayload)
        );

        // --- Missing user_id ---
        Map<String, Object> noUserId = new HashMap<>(payload);
        noUserId.remove("user_id");
        assertThrows(BadInputException.class, () ->
                accountManager.sendUserData(noUserId)
        );

        // --- Missing name ---
        Map<String, Object> noName = new HashMap<>(payload);
        noName.remove("name");
        assertThrows(BadInputException.class, () ->
                accountManager.sendUserData(noName)
        );

        // --- Missing email ---
        Map<String, Object> noEmail = new HashMap<>(payload);
        noEmail.remove("email");
        assertThrows(BadInputException.class, () ->
                accountManager.sendUserData(noEmail)
        );

        // --- Missing country ---
        Map<String, Object> noCountry = new HashMap<>(payload);
        noCountry.remove("country");
        assertThrows(BadInputException.class, () ->
                accountManager.sendUserData(noCountry)
        );

        // --- Missing userType ---
        Map<String, Object> missingType = new HashMap<>(payload);
        missingType.remove("userType");
        assertThrows(BadInputException.class, () ->
                accountManager.sendUserData(missingType)
        );

        // --- Invalid userType ---
        Map<String, Object> invalidType = new HashMap<>(payload);
        invalidType.put("userType", "INVALID");
        assertThrows(BadInputException.class, () ->
                accountManager.sendUserData(invalidType)
        );

        // --- Invalid birthDate format for STUDENT ---
        Map<String, Object> invalidDate = new HashMap<>(payload);
        invalidDate.put("birthDate", "Not-A-Date");
        assertThrows(BadInputException.class, () ->
                accountManager.sendUserData(invalidDate)
        );

        // --- For COMPANY: missing vatNumber ---
        Map<String, Object> companyPayload = new HashMap<>(payload);
        companyPayload.put("userType", "COMPANY");
        companyPayload.remove("surname");
        companyPayload.remove("uniVat");
        companyPayload.remove("birthDate");
        Map<String, Object> missingVat = new HashMap<>(companyPayload);
        missingVat.remove("vatNumber");
        assertThrows(BadInputException.class, () ->
                accountManager.sendUserData(missingVat)
        );

        // --- For UNIVERSITY: missing uniDescription ---
        Map<String, Object> uniPayload = new HashMap<>(payload);
        uniPayload.put("userType", "UNIVERSITY");
        uniPayload.put("vatNumber", 1010);
        uniPayload.put("uniDescription", "Desc");
        uniPayload.remove("surname");
        uniPayload.remove("uniVat");
        uniPayload.remove("birthDate");

        Map<String, Object> missingUniDesc = new HashMap<>(uniPayload);
        missingUniDesc.remove("uniDescription");
        assertThrows(BadInputException.class, () ->
                accountManager.sendUserData(missingUniDesc)
        );
    }


    @Test
    void testConfirmUser() throws BadInputException {
        // If account not found => NotFoundException
        when(accountRepository.findById("UUID-999")).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () ->
                accountManager.confirmUser("UUID-999")
        );

        // If account found => Create user based on userType
        Account studentAcc = new Account("UUID-123", "Alice", "alice@example.com", "IT",
                UserType.STUDENT, false, "Smith", 1234, LocalDate.now(), null, null, null);
        when(accountRepository.findById("UUID-123")).thenReturn(Optional.of(studentAcc));

        // University for the student
        University uni = new University();
        uni.setVatNumber(1234);
        when(universityRepository.findByVatNumber(1234)).thenReturn(uni);
        // Mock repository saves
        when(studentRepository.save(any(Student.class))).thenAnswer(inv -> inv.getArgument(0));
        when(accountRepository.save(any(Account.class))).thenAnswer(inv -> inv.getArgument(0));

        Account confirmedStud = accountManager.confirmUser("UUID-123");
        assertTrue(confirmedStud.getValidate());
        verify(studentRepository).save(any(Student.class));

        // Company user
        Account compAcc = new Account("UUID-456", "Google", "google@example.com", "US",
                UserType.COMPANY, false, null, null, null, 9999, null, "location");
        when(accountRepository.findById("UUID-456")).thenReturn(Optional.of(compAcc));
        when(companyRepository.save(any(Company.class))).thenAnswer(inv -> inv.getArgument(0));
        Account confirmedComp = accountManager.confirmUser("UUID-456");
        assertTrue(confirmedComp.getValidate());
        verify(companyRepository).save(any(Company.class));

        // University user
        Account uniAcc = new Account("UUID-789", "MIT", "mit@example.com", "US",
                UserType.UNIVERSITY, false, null, null, null, 1111, "desc", "location");
        when(accountRepository.findById("UUID-789")).thenReturn(Optional.of(uniAcc));
        when(universityRepository.save(any(University.class))).thenAnswer(inv -> inv.getArgument(0));
        Account confirmedUni = accountManager.confirmUser("UUID-789");
        assertTrue(confirmedUni.getValidate());
        verify(universityRepository).save(any(University.class));
    }

    @Test
    void testGetUniversitiesMap() {
        // Return a list of universities
        University u1 = new University();
        u1.setName("UniA");
        u1.setVatNumber(1111);
        University u2 = new University();
        u2.setName("UniB");
        u2.setVatNumber(2222);
        when(universityRepository.findAll()).thenReturn(List.of(u1, u2));

        Map<String, Integer> result = accountManager.getUniversitiesMap();
        assertEquals(2, result.size());
        assertEquals(1111, result.get("UniA"));
        assertEquals(2222, result.get("UniB"));

        // If empty list => empty map
        when(universityRepository.findAll()).thenReturn(Collections.emptyList());
        Map<String, Integer> emptyResult = accountManager.getUniversitiesMap();
        assertTrue(emptyResult.isEmpty());
    }

    @Test
    void testGetAccountBy() {
        // Success
        Account acc = new Account("UUID-123", "Alice", "alice@example.com", "IT",
                UserType.STUDENT, false, "Smith", 1234, LocalDate.now(), null, null, null);
        when(accountRepository.findById("UUID-123")).thenReturn(Optional.of(acc));
        Account found = accountManager.getAccountBy("UUID-123");
        assertEquals("Alice", found.getName());

        // Not found
        when(accountRepository.findById("UUID-999")).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () ->
                accountManager.getAccountBy("UUID-999")
        );
    }
}
