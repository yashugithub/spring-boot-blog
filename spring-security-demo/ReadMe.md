### Spring Security
Go through the <a href="https://spring.io/guides/topicals/spring-security-architecture">Spring Security Architecture</a>

WebSecurityConfigurerAdapter is deprecated in latest Spring Security. Use SecurityFilterChain <a href="https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter">Spring Security without the WebSecurityConfigurerAdapter</a>

##### Fetch user information from the request using any of the method

###### 1. @AuthenticationPrincipal
###### 2. SecurityContextHolder
###### 3. HttpServletRequest

Note: Examples find at UserLoginController.java


