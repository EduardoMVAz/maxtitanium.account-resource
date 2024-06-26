package max.account;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class AccountResource implements AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/accounts/info")
	public ResponseEntity<Map<String, String>> info() {
        return new ResponseEntity<Map<String, String>>(
            Map.ofEntries(
                Map.entry("microservice.name", AccountApplication.class.getSimpleName()),
                Map.entry("os.arch", System.getProperty("os.arch")),
                Map.entry("os.name", System.getProperty("os.name")),
                Map.entry("os.version", System.getProperty("os.version")),
                Map.entry("file.separator", System.getProperty("file.separator")),
                Map.entry("java.class.path", System.getProperty("java.class.path")),
                Map.entry("java.home", System.getProperty("java.home")),
                Map.entry("java.vendor", System.getProperty("java.vendor")),
                Map.entry("java.vendor.url", System.getProperty("java.vendor.url")),
                Map.entry("java.version", System.getProperty("java.version")),
                Map.entry("line.separator", System.getProperty("line.separator")),
                Map.entry("path.separator", System.getProperty("path.separator")),
                Map.entry("user.dir", System.getProperty("user.dir")),
                Map.entry("user.home", System.getProperty("user.home")),
                Map.entry("user.name", System.getProperty("user.name")),
                Map.entry("jar", new java.io.File(
                    AccountApplication.class.getProtectionDomain()
                        .getCodeSource()
                        .getLocation()
                        .getPath()
                    ).toString())
            ), HttpStatus.OK
        );
	}

	@Override
	public ResponseEntity<AccountOut> read(String id) {

        Account dbAccount = accountService.read(id);

		return ResponseEntity.ok(
            AccountOut.builder()
                .id(dbAccount.id())
                .name(dbAccount.name())
                .email(dbAccount.email())
                .build()
        );
	}

	@Override
	public ResponseEntity<AccountOut> create(AccountIn accountIn) {
        
        Account account = AccountParser.to(accountIn);

        account = accountService.create(account);

        return ResponseEntity.created(
            ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(account.id())
                .toUri())
            .body(AccountParser.to(account));
	}

	@Override
	public ResponseEntity<AccountOut> update(String id, AccountIn accountIn) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'update'");
	}

    @Override
    public ResponseEntity<AccountOut> login(LoginIn in) {
        Account account = accountService.login(in.email(), in.password());
        return ResponseEntity.ok(AccountParser.to(account));
    }

	@Override
	public ResponseEntity<AccountOut> delete(String id) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'delete'");
	}

    @Override
    public ResponseEntity<AccountOut> list(String idUser, String roleUser) {
        // TODO Auto-generated method stub
        final AccountOut account = AccountOut.builder()
            .id(idUser)
            .name(roleUser)
            .build();
        return ResponseEntity.ok(account);
    }
    
}
