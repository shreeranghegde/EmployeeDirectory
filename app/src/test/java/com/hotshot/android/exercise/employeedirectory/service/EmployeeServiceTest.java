package com.hotshot.android.exercise.employeedirectory.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.content.Context;

import com.hotshot.android.exercise.employeedirectory.activity.MainActivity;
import com.hotshot.android.exercise.employeedirectory.constants.Network;
import com.hotshot.android.exercise.employeedirectory.constants.TestData;
import com.hotshot.android.exercise.employeedirectory.types.Employee;
import com.hotshot.android.exercise.employeedirectory.types.ResponseType;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.LooperMode;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

@RunWith(RobolectricTestRunner.class)
@LooperMode(LooperMode.Mode.LEGACY)
public class EmployeeServiceTest {
    Context context;
    EmployeeService employeeService;
    @Mock
    OkHttpClient mockClient;

    @Mock
    Call mockCall;

    @Mock EmployeeService.NetworkCallCompletedListener mockedListener;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);

        context = Robolectric.buildActivity(MainActivity.class).get();
        employeeService = new EmployeeService(context);
        employeeService = spy(employeeService);
        employeeService.client = mockClient;
        employeeService.listener = mockedListener;
        when(mockClient.newCall(any(Request.class))).thenReturn(mockCall);
    }

    @Test
    public void sanityTest() {
        Assert.assertNotNull(employeeService);
    }

    @Test
    public void testEmployeeService_GetEmployees() {
        when(employeeService.getEmployees()).thenReturn(TestData.getEmployeeList());
        List<Employee> actualEmployeeList = employeeService.getEmployees();
        Assert.assertEquals(TestData.getEmployeeList(), actualEmployeeList);
    }

    @Test
    public void testEmployeeService_FetchEmployees_Success() {
        String actualResponseString = TestData.getResponseBodyValidDataString();
        ArgumentCaptor<ResponseType> argumentCaptor = ArgumentCaptor.forClass(ResponseType.class);
        setupNetworkCallTestingConfiguration(actualResponseString, true);

        verify(mockedListener, times(1)).fetchCompleted(argumentCaptor.capture());
        Assert.assertEquals(ResponseType.VALID, argumentCaptor.getValue());
        verify(employeeService, times(1)).handleResponse(actualResponseString);
        verify(employeeService, times(1)).handleUiThreadTasks();
    }

    @Test
    public void testEmployeeService_FetchEmployees_EmptyResponseString() {
        String actualResponseString = "";
        ArgumentCaptor<ResponseType> argumentCaptor = ArgumentCaptor.forClass(ResponseType.class);
        setupNetworkCallTestingConfiguration(actualResponseString, true);

        verify(mockedListener, times(1)).fetchCompleted(argumentCaptor.capture());
        Assert.assertEquals(ResponseType.EMPTY, argumentCaptor.getValue());
        verify(employeeService, times(1)).handleResponse(actualResponseString);
        verify(employeeService, times(1)).handleUiThreadTasks();
    }

    @Test
    public void testEmployeeService_FetchEmployees_MalformedResponseString() {
        String actualResponseString = TestData.getResponseBodyMalformedDataString();
        ArgumentCaptor<ResponseType> argumentCaptor = ArgumentCaptor.forClass(ResponseType.class);
        setupNetworkCallTestingConfiguration(actualResponseString, true);

        verify(mockedListener, times(1)).fetchCompleted(argumentCaptor.capture());
        Assert.assertEquals(ResponseType.MALFORMED, argumentCaptor.getValue());
        verify(employeeService, times(1)).handleResponse(actualResponseString);
        verify(employeeService, times(1)).handleUiThreadTasks();
    }

    @Test
    public void testEmployeeService_FetchEmployees_Error() {
        String actualResponseString = "";
        ArgumentCaptor<ResponseType> argumentCaptor = ArgumentCaptor.forClass(ResponseType.class);
        setupNetworkCallTestingConfiguration(actualResponseString, false);

        verify(mockedListener, times(1)).fetchCompleted(argumentCaptor.capture());
        Assert.assertEquals(ResponseType.NETWORK_ERROR, argumentCaptor.getValue());
        verify(employeeService, times(0)).handleResponse(actualResponseString);
        verify(employeeService, times(0)).handleUiThreadTasks();
    }

    private void setupNetworkCallTestingConfiguration(String expectedResponse,
                                                      boolean isSuccessful) {

        Request request = new Request.Builder()
                .url(Network.URL)
                .build();
        ResponseBody responseBody = ResponseBody.create(expectedResponse,
                                                        MediaType.parse("application/json"));

        Response response = new Response.Builder()
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .code(200)
                .message("OK")
                .body(responseBody)
                .build();

        if(isSuccessful){
            successInvocation(response);
        } else {
            errorInvocation();
        }


        doNothing().when(mockedListener).fetchCompleted(any());
        Robolectric.getForegroundThreadScheduler().post(() -> {
            employeeService.fetchEmployees(Network.URL);
        });
    }

    private void successInvocation(Response response) {
        doAnswer(invocation -> {
            Callback callback = invocation.getArgument(0);
            callback.onResponse(mockCall, response);
            return null;
        }).when(mockCall).enqueue(any(Callback.class));
    }

    private void errorInvocation() {
        doAnswer(invocation -> {
            Callback callback = invocation.getArgument(0);
            callback.onFailure(mockCall, new IOException());
            return null;
        }).when(mockCall).enqueue(any(Callback.class));
    }
}
