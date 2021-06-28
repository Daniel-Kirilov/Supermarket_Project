package com.endava.supermarket.controller_test;

import com.endava.supermarket.controller.ItemController;
import com.endava.supermarket.service.ItemService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ItemControllerTest.class)
@Import(ItemController.class)
public class ItemControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ItemService itemService;

    @MockBean
    private ModelMapper modelMapper;

    @Test
    public void whenValidInput_createItem_thenReturnsStatusCREATED() throws Exception{
        mvc.perform(MockMvcRequestBuilders
        .post("/v1/items/create-item")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        //.characterEncoding("UTF-8")
        .content("{\"name\":\"Bread\",\"itemType\":\"FOOD\"}"))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isCreated());
    }
}
