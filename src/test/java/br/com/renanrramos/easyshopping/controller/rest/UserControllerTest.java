package br.com.renanrramos.easyshopping.controller.rest;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import br.com.renanrramos.easyshopping.service.impl.AuthenticationServiceImpl;
import br.com.renanrramos.easyshopping.service.impl.UserService;

/**
 * 
 * @author renan.ramos
 * Test for {@link UserController }
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = UserController.class)
@WebAppConfiguration
@WithMockUser(roles = {"ADMINISTRATOR", "CUSTOMER", "easy-shopping-user"})
public class UserControllerTest {

	private static final String BASE_URL = "/api/users";

	@InjectMocks
	private UserController userController;

	@MockBean
	private UserService userService;

	@MockBean
	private AuthenticationServiceImpl authenticationServiceImpl;

	private MockMvc mockMvc;

	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
	}

	@Test
	public void getCustomerProfile_withValidTokenId_shouldReturnUser() throws Exception {
		when(userService.findUserByTokenId(anyString())).thenReturn(Optional.of(EasyShoppingUtil.getUserInstance()));
		when(authenticationServiceImpl.getName()).thenReturn("userName");
		
		mockMvc.perform(get(BASE_URL + "/profile"))
		.andExpect(status().isOk());

		verify(userService, times(1)).findUserByTokenId(anyString());
		verify(authenticationServiceImpl, times(1)).getName();
	}

	@Test
	public void getCustomerProfile_withInvalidTokenId_shouldReturnUser() throws Exception {
		when(userService.findUserByTokenId(anyString())).thenReturn(Optional.empty());
		when(authenticationServiceImpl.getName()).thenReturn("userName");
		
		mockMvc.perform(get(BASE_URL + "/profile"))
		.andExpect(status().isOk());

		verify(userService, times(1)).findUserByTokenId(anyString());
		verify(authenticationServiceImpl, times(1)).getName();
	}
}
