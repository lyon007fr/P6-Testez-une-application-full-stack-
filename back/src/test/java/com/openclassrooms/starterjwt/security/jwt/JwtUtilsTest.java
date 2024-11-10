
  package com.openclassrooms.starterjwt.security.jwt;
  
  import static org.junit.jupiter.api.Assertions.*; import static
  org.mockito.Mockito.*;
  
  import java.lang.reflect.Field; import java.util.Date;
  
  import org.junit.jupiter.api.BeforeEach; import org.junit.jupiter.api.Test;
  import org.mockito.InjectMocks; import org.mockito.Mock; import
  org.mockito.MockitoAnnotations; import
  org.springframework.beans.factory.annotation.Value; import
  org.springframework.security.core.Authentication;
  
  import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;
  
  import io.jsonwebtoken.Jwts; import io.jsonwebtoken.SignatureAlgorithm;
  
  public class JwtUtilsTest {
  
  @InjectMocks private JwtUtils jwtUtils;
  
  @Mock private Authentication authentication;
  
  @Mock private UserDetailsImpl userDetails;
  
  @Value("${oc.app.jwtSecret}") private String jwtSecret = "testSecret";
  
  @Value("${oc.app.jwtExpirationMs}") private int jwtExpirationMs = 3600000; //
  1 hour
  
  @BeforeEach void setUp() throws Exception {
  MockitoAnnotations.openMocks(this);
  
  Field secretField = JwtUtils.class.getDeclaredField("jwtSecret");
  secretField.setAccessible(true); secretField.set(jwtUtils, "testSecret");
  
  Field expirationField = JwtUtils.class.getDeclaredField("jwtExpirationMs");
  expirationField.setAccessible(true); expirationField.set(jwtUtils, 60000); //
  1 minute }
  
  @Test public void testGenerateJwtToken() {
  when(authentication.getPrincipal()).thenReturn(userDetails);
  when(userDetails.getUsername()).thenReturn("testUser");
  
  String token = jwtUtils.generateJwtToken(authentication);
  
  assertNotNull(token); String username =
  Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().
  getSubject(); assertEquals("testUser", username); }
  
  @Test public void testGetUserNameFromJwtToken() { String token =
  Jwts.builder() .setSubject("testUser") .setIssuedAt(new Date())
  .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
  .signWith(SignatureAlgorithm.HS512, jwtSecret) .compact();
  
  String username = jwtUtils.getUserNameFromJwtToken(token);
  
  assertEquals("testUser", username); }
  
  @Test public void testValidateJwtToken() { String validToken = Jwts.builder()
  .setSubject("testUser") .setIssuedAt(new Date()) .setExpiration(new Date((new
  Date()).getTime() + jwtExpirationMs)) .signWith(SignatureAlgorithm.HS512,
  jwtSecret) .compact();
  
  assertTrue(jwtUtils.validateJwtToken(validToken));
  
  String invalidToken = validToken + "invalid";
  assertFalse(jwtUtils.validateJwtToken(invalidToken)); }
  
  @Test public void testValidateJwtToken_MalformedJwtException() { String
  malformedToken = "malformed.token";
  
  assertFalse(jwtUtils.validateJwtToken(malformedToken)); }
  
  @Test public void testValidateJwtToken_ExpiredJwtException() { String
  expiredToken = Jwts.builder() .setSubject("testUser") .setIssuedAt(new
  Date(System.currentTimeMillis() - 10000)) .setExpiration(new
  Date(System.currentTimeMillis() - 5000)) .signWith(SignatureAlgorithm.HS512,
  jwtSecret) .compact();
  
  assertFalse(jwtUtils.validateJwtToken(expiredToken)); }
  
  @Test public void testValidateJwtToken_UnsupportedJwtException() { String
  unsupportedToken = Jwts.builder() .setPayload("unsupported")
  .signWith(SignatureAlgorithm.HS512, jwtSecret) .compact();
  
  assertFalse(jwtUtils.validateJwtToken(unsupportedToken)); }
  
  @Test public void testValidateJwtToken_IllegalArgumentException() { String
  emptyToken = "";
  
  assertFalse(jwtUtils.validateJwtToken(emptyToken)); } }
 