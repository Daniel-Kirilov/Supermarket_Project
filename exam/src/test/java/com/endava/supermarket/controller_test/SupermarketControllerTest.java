package com.endava.supermarket.controller_test;

import com.endava.supermarket.controller.SupermarketController;
import com.endava.supermarket.service.SuperMarketService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.util.LinkedMultiValueMap;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(SupermarketControllerTest.class)
@Import(SupermarketController.class)
public class SupermarketControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private SuperMarketService supermarketService;

    @MockBean
    private ModelMapper modelMapper;

    @Test
    public void whenValidInput_createSupermarket_thenReturnsStatusCREATED() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .post("/v1/supermarkets/create-supermarket")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Lidl-Lovech\",\"address\":\"Bulevard-Bulgariq 48\"," +
                        "\"phoneNumber\":\"0887569455\",\"workHours\":\"08:22:30\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated());
    }


    @Test
    public void whenValidInput_addItemsToSupermarket_thenReturnsStatusOK() throws Exception {

        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("superMarketId", "402880ea79e0b0480179e0b0b0330000");
        requestParams.add("itemsId", "402880ea79e0b0480179e0b0b0330001");
        requestParams.add("itemsId", "402880ea79e0b0480179e000b0330002");
        requestParams.add("itemsPrice", Double.toString(54.5));
        requestParams.add("itemsPrice", Double.toString(20.5));
        mvc.perform(MockMvcRequestBuilders
                .post("/v1/supermarkets/add-items")
                .params(requestParams))
                .andExpect(status().isOk());
    }

    @Test
    public void whenInvalidPriceInput_addItemsToSupermarket_thenReturnsStatusBadRequest() throws Exception {

        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("superMarketId", "402880ea79e0b0480179e0b0b0330000");
        requestParams.add("itemsId", "402880ea79e0b0480179e0b0b0330001");
        requestParams.add("itemsId", "402880ea79e0b0480179e000b0330002");
        requestParams.add("itemsPrice", Double.toString(54.5));
        requestParams.add("itemsPrice", "Invalid price");
        mvc.perform(MockMvcRequestBuilders
                .post("/v1/supermarkets/add-items")
                .params(requestParams))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void whenValidInput_getSupermarketById_thenReturnsStatusOK() throws Exception {


        mvc.perform(MockMvcRequestBuilders
                .get("/v1/supermarkets/get-by-id/{superMarketId}", "402880ea79e0b0480179e0b0b0330000"))
                .andExpect(status().isOk());
    }

    @Test
    public void whenValidInput_getSupermarketNameAndAddress_thenReturnsStatusOK() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/v1/supermarkets/get-by-name-and-address")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Lidl-Lovech\",\"address\":\"Bulevard-Bulgariq 48\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }
}
