package com.crestasom.dms;

import static org.junit.Assert.assertEquals;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;

import com.crestasom.dms.dto.DroneDTO;
import com.crestasom.dms.dto.MedicationDTO;
import com.crestasom.dms.model.ResponseBean;
import com.crestasom.dms.model.enums.Model;
import com.crestasom.dms.model.enums.State;
import com.crestasom.dms.model.request.LoadMedicationItemsRequest;
import com.crestasom.dms.service.DroneService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = DmsApplication.class)
@AutoConfigureMockMvc
@AutoConfigureRestDocs(outputDir = "src/docs")
//@TestPropertySource(locations = "classpath:application-test.properties")
class DroneControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private DroneService service;

	private static final String REGISTER_DRONE_URI = "/register-drone";
	private static final String LOAD_MEDICATION = "/load-medication";

	@BeforeEach
	public void setUp() {
		service.removeAllDrone();
	}

	@Test
	void testInsertMockRecord() throws Exception {
		DroneDTO dto = DroneDTO.builder().batteryCapacity(75).maxWeight(500.0).model(Model.LIGHT_WEGHT.toString())
				.serialNumber("12345").state(State.IDLE.toString()).build();
		ObjectMapper mapper = new ObjectMapper();
		String requestJson = objtoJson(dto);
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.post(REGISTER_DRONE_URI).contentType(MediaType.APPLICATION_JSON)
						.content(requestJson))
				.andExpect(MockMvcResultMatchers.status().isOk()).andDo(document(REGISTER_DRONE_URI,
						preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
				.andReturn();
		ResponseBean resp = mapper.readValue(result.getResponse().getContentAsString(), ResponseBean.class);
		assertEquals(200, resp.getRespCode().intValue());
	}

	@Test
	void testInsertMockRecordDuplicateDroneError() throws Exception {
		insertMockRecord();
		DroneDTO dto = DroneDTO.builder().batteryCapacity(75).maxWeight(500.0).model(Model.LIGHT_WEGHT.toString())
				.serialNumber("12345").state(State.IDLE.toString()).build();
		ObjectMapper mapper = new ObjectMapper();
		String requestJson = objtoJson(dto);
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.post(REGISTER_DRONE_URI).contentType(MediaType.APPLICATION_JSON)
						.content(requestJson))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError()).andDo(document(REGISTER_DRONE_URI,
						preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
				.andReturn();
		ResponseBean resp = mapper.readValue(result.getResponse().getContentAsString(), ResponseBean.class);
		assertEquals(400, resp.getRespCode().intValue());

	}

	@Test
	void testLoadMedication() throws Exception {
		insertMockRecord();
		LoadMedicationItemsRequest req = createMockRecord();
		ObjectMapper mapper = new ObjectMapper();
		String requestJson = objtoJson(req);
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.post(LOAD_MEDICATION).contentType(MediaType.APPLICATION_JSON)
						.content(requestJson))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(document(LOAD_MEDICATION, preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
				.andReturn();
		ResponseBean resp = mapper.readValue(result.getResponse().getContentAsString(), ResponseBean.class);
		assertEquals(200, resp.getRespCode().intValue());

	}

	@Test
	void testLoadMedicationNoDroneFound() throws Exception {
		LoadMedicationItemsRequest req = createMockRecord();
		ObjectMapper mapper = new ObjectMapper();
		String requestJson = objtoJson(req);
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.post(LOAD_MEDICATION).contentType(MediaType.APPLICATION_JSON)
						.content(requestJson))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError())
				.andDo(document(LOAD_MEDICATION, preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
				.andReturn();
		ResponseBean resp = mapper.readValue(result.getResponse().getContentAsString(), ResponseBean.class);
		assertEquals(400, resp.getRespCode().intValue());

	}

	@Test
	void testLoadMedicationNoMedicationListFound() throws Exception {
		insertMockRecord();
		LoadMedicationItemsRequest req = createMockRecord();
		req.setMedicationItemList(null);
		ObjectMapper mapper = new ObjectMapper();
		String requestJson = objtoJson(req);
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.post(LOAD_MEDICATION).contentType(MediaType.APPLICATION_JSON)
						.content(requestJson))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError())
				.andDo(document(LOAD_MEDICATION, preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
				.andReturn();
		ResponseBean resp = mapper.readValue(result.getResponse().getContentAsString(), ResponseBean.class);
		assertEquals(400, resp.getRespCode().intValue());

	}

	@Test
	void testLoadMedicationDroneNotLoadable() throws Exception {
		insertMockRecord();
		LoadMedicationItemsRequest req = new LoadMedicationItemsRequest();
		req.setDroneSerialNumber("12345");
		List<MedicationDTO> medList = new ArrayList<>();
		medList.add(MedicationDTO.builder().name("paracetamol").code("CTML").weight(400.0).build());
		medList.add(MedicationDTO.builder().name("brufin").code("BFN").weight(200.0).build());
		req.setMedicationItemList(medList);
		ObjectMapper mapper = new ObjectMapper();
		String requestJson = objtoJson(req);
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.post(LOAD_MEDICATION).contentType(MediaType.APPLICATION_JSON)
						.content(requestJson))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(document(LOAD_MEDICATION, preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
				.andReturn();
		ResponseBean resp = mapper.readValue(result.getResponse().getContentAsString(), ResponseBean.class);
		assertEquals(401, resp.getRespCode().intValue());

	}

	@Test
	void testLoadMedicationDroneLoading() throws Exception {
		insertMockRecordLoading();
		LoadMedicationItemsRequest req = createMockRecord();

		ObjectMapper mapper = new ObjectMapper();
		String requestJson = objtoJson(req);
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.post(LOAD_MEDICATION).contentType(MediaType.APPLICATION_JSON)
						.content(requestJson))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(document(LOAD_MEDICATION, preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
				.andReturn();
		ResponseBean resp = mapper.readValue(result.getResponse().getContentAsString(), ResponseBean.class);
		assertEquals(402, resp.getRespCode().intValue());

	}

	@Test
	void testLoadMedicationDroneBatteryLow() throws Exception {
		insertMockRecordLowBattery();
		LoadMedicationItemsRequest req = createMockRecord();

		ObjectMapper mapper = new ObjectMapper();
		String requestJson = objtoJson(req);
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.post(LOAD_MEDICATION).contentType(MediaType.APPLICATION_JSON)
						.content(requestJson))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(document(LOAD_MEDICATION, preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
				.andReturn();
		ResponseBean resp = mapper.readValue(result.getResponse().getContentAsString(), ResponseBean.class);
		assertEquals(403, resp.getRespCode().intValue());

	}

	private LoadMedicationItemsRequest createMockRecord() {
		LoadMedicationItemsRequest req = new LoadMedicationItemsRequest();
		req.setDroneSerialNumber("12345");
		List<MedicationDTO> medList = new ArrayList<>();
		medList.add(MedicationDTO.builder().name("paracetamol").code("CTML").weight(100.0).build());
		medList.add(MedicationDTO.builder().name("brufin").code("BFN").weight(100.0).build());
		req.setMedicationItemList(medList);
		return req;
	}

	static String objtoJson(Object obj) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(obj);
		return requestJson;
	}

	void insertMockRecord() {
		DroneDTO dto = DroneDTO.builder().batteryCapacity(75).maxWeight(500.0).model(Model.LIGHT_WEGHT.toString())
				.serialNumber("12345").state(State.IDLE.toString()).build();
		service.register(dto);
	}

	void insertMockRecordLoading() {
		DroneDTO dto = DroneDTO.builder().batteryCapacity(75).maxWeight(500.0).model(Model.LIGHT_WEGHT.toString())
				.serialNumber("12345").state(State.LOADING.toString()).build();
		service.register(dto);
	}

	void insertMockRecordLowBattery() {
		DroneDTO dto = DroneDTO.builder().batteryCapacity(20).maxWeight(500.0).model(Model.LIGHT_WEGHT.toString())
				.serialNumber("12345").state(State.IDLE.toString()).build();
		service.register(dto);
	}

}
