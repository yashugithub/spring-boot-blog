### Spring Security
Go through the <a href="https://spring.io/guides/topicals/spring-security-architecture">Spring Security Architecture</a>

WebSecurityConfigurerAdapter is deprecated in latest Spring Security. Use SecurityFilterChain <a href="https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter">Spring Security without the WebSecurityConfigurerAdapter</a>

##### Fetch user information from the request using any of the method

###### 1. @AuthenticationPrincipal
###### 2. SecurityContextHolder
###### 3. HttpServletRequest

Note: Examples find at UserLoginController.java

#### Custom Authentication provider
 
 Next we will learn about how to introduce the custom authentication provider. 

 Usecase: Authenticate the user details which are stored in h2 db and add couple more checks on user such as user is active and so on
 
- Step 1 :- Introduce the CustomUserAuthenticationProvider
    - extend AbstractUserDetailsAuthenticationProvider
    - Implement the additionalAuthenticationChecks() and retrieveUser()

- Step 2 :- Register CustomUserAuthenticationProvider to AuthenticationManager
    - extend GlobalAuthenticationConfigurerAdapter
    - register auth provider in init() 
    
Difference Between        @EnableGlobalMethodSecurity and @EnableWebSecurity

    EnableGlobalMethodSecurity provides AOP security on methods. 
    Some of the annotations that it provides are PreAuthorize, PostAuthorize. 
    It also has support for JSR-250

    EnableWebSecurity will provide configuration via HttpSecurity. 
    It's the configuration you could find with <http></http> tag in xml configuration, 
    it allows you to configure your access based on urls patterns,
    the authentication endpoints, handlers etc...


