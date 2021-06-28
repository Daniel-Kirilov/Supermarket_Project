package com.endava.supermarket.controller_test;

import com.endava.supermarket.controller.PurchaseController;
import com.endava.supermarket.service.PurchaseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PurchaseControllerTest.class)
@Import(PurchaseController.class)
public class PurchaseControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private  PurchaseService purchaseService;

    @MockBean
    private ModelMapper modelMapper;

    @Test
    public void whenValidInput_makePurchase_thenReturnsStatusCREATED() throws Exception {

        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("superMarketId", "402880ea79e0b0480179e0b0b0330000");
        requestParams.add("itemsId", "402880ea79e0b0480179e0b0b0330000");
        requestParams.add("itemsId", "402880ea79e0b0480179e000b0330000");
        requestParams.add("paymentType", "CASH");
        requestParams.add("cashAmount", "50.3");
        mvc.perform(MockMvcRequestBuilders
                .post("/v1/purchases/buy-items")
                .params(requestParams))
                .andExpect(status().isCreated());
    }

    @Test
    public void whenInvalidPaymentType_makePurchase_thenReturnsStatusBAD_REQUEST() throws Exception {

        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("superMarketId", "402880ea79e0b0480179e0b0b0330000");
        requestParams.add("itemsId", "402880ea79e0b0480179e0b0b0330000");
        requestParams.add("itemsId", "402880ea79e0b0480179e000b0330000");
        requestParams.add("paymentType", "INVALIDFIELD");
        requestParams.add("cashAmount", "50.3");
        mvc.perform(MockMvcRequestBuilders
                .post("/v1/purchases/buy-items")
                .params(requestParams))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenInvalidCashAmount_makePurchase_thenReturnsStatusBAD_REQUEST() throws Exception {

        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("superMarketId", "402880ea79e0b0480179e0b0b0330000");
        requestParams.add("itemsId", "402880ea79e0b0480179e0b0b0330000");
        requestParams.add("itemsId", "402880ea79e0b0480179e000b0330000");
        requestParams.add("paymentType", "CASH");
        requestParams.add("cashAmount", "INVALIDFIELD");
        mvc.perform(MockMvcRequestBuilders
                .post("/v1/purchases/buy-items")
                .params(requestParams))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void getAllPurchases_shouldReturnStatusOK() throws Exception {

        mvc.perform(MockMvcRequestBuilders
                .get("/v1/purchases/get-all"))
                .andExpect(status().isOk());
    }
}
