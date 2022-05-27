package uz.venkon;

import com.neovisionaries.ws.client.*;
import com.google.gson.Gson;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Base64;

public class Main {

    private static WebSocket _ws = null;
    private static Gson _gson = new Gson();
    private static byte [] bytes;

    private static Answer _answer;
    private static Message _message;
    private static String _answerString;

    private static String _uri = "ws://127.0.0.1:64646/service/cryptapi";
    private static int _timeout = 5000;

    private static boolean _connected;
    private static boolean _serverAnswered;

    public static void main(String[] args) {

        // 1.
        try {
            initializeWebSocketClientObject();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        // 3.
        if(args.length > 0) {
            sendMessageToWebSocket(args);
        }

        return;

    }

    private static void initializeWebSocketClientObject() throws Exception{

        // Create a WebSocket factory. The timeout value remains 0.
        WebSocketFactory factory = new WebSocketFactory();

        // Create a WebSocket with a socket connection timeout value.
        _ws = factory.createSocket(_uri, _timeout);

        // Add an "origin" header
        _ws.addHeader("Origin", "localhost");

        //
        // Register a listeners to receive WebSocket events.
        _ws.addListener(new WebSocketAdapter() {
            @Override
            public void onConnected(WebSocket websocket, Map<String,List<String>> headers) throws Exception {
                //System.out.println(201);
                return;
            }
        });

        _ws.addListener(new WebSocketAdapter() {
            @Override
            public void onConnectError(WebSocket websocket, WebSocketException cause) throws Exception {
                //
            }
        });

        _ws.addListener(new WebSocketAdapter() {
            @Override
            public void onTextMessage(WebSocket websocket, String message) throws Exception {
                /**
                 * onTextMessage listener
                 */
                _answer = _gson.fromJson(message, Answer.class);
                _answerString = message;

                byte[] array = _answerString.getBytes(StandardCharsets.UTF_16);

                System.out.write(array);

                //System.out.println(array.toString());
                _serverAnswered = true;
                _ws.disconnect();
            }
        });

        _ws.addListener(new WebSocketAdapter() {
            @Override
            public void onBinaryMessage(WebSocket websocket, byte[] binary) throws Exception {

            }
        });
        //

        // Для корректной работы
        _ws.addHeader("origin", "localhost");

        _ws.connect();

        //

    }


    /**
     * handshaking
     **/
    private static void start() {

        String message = "{name: 'apikey', arguments: ['localhost', '96D0C1491615C82B9A54D9989779DF825B690748224C2B04F500F370D51827CE2644D8D4A82C18184D73AB8530BB8ED537269603F61DB0D03D2104ABF789970B', '127.0.0.1', 'A7BCFA5D490B351BE0754130DF03A068F855DB4333D43921125B9CF2670EF6A40370C646B90401955E1F7BC9CDBF59CE0B2C5467D820BE189C845D0B79CFC96F', 'null', 'E0A205EC4E7B78BBB56AFF83A733A1BB9FD39D562E67978CC5E7D73B0951DB1954595A20672A63332535E13CC6EC1E1FC8857BB09E0855D7E76E411B6FA16E9D']}";

        _ws.sendText(message);

        _serverAnswered = false;

        while(!_serverAnswered);

    }

    /**
     * Sending messages
     **/
    private static void sendMessageToWebSocket(String[] _args) {

        String requiredFunction = _args[0];

       if(requiredFunction.equals("-get_version"))
        {
            System.out.println(getVersion());
            _serverAnswered = true;
        }
       else {
            initializeMessage(_args);

            _serverAnswered = false;
            String mess = _gson.toJson(_message, Message.class);
            _ws.sendText(mess);
           //while(!_serverAnswered);
            //_ws.disconnect();
        }

    }

    private static String getVersion() {

        return "1.1";

    }

    private static void handshaking() {
        _message = new Message("apikey", "localhost", "96D0C1491615C82B9A54D9989779DF825B690748224C2B04F500F370D51827CE2644D8D4A82C18184D73AB8530BB8ED537269603F61DB0D03D2104ABF789970B", "127.0.0.1", "A7BCFA5D490B351BE0754130DF03A068F855DB4333D43921125B9CF2670EF6A40370C646B90401955E1F7BC9CDBF59CE0B2C5467D820BE189C845D0B79CFC96F", "null", "E0A205EC4E7B78BBB56AFF83A733A1BB9FD39D562E67978CC5E7D73B0951DB1954595A20672A63332535E13CC6EC1E1FC8857BB09E0855D7E76E411B6FA16E9D");
        _serverAnswered = false;
        String mess = _gson.toJson(_message, Message.class);
        _ws.sendText(mess);
        while(!_serverAnswered);
    }

    private static void initializeMessage(String[] _args) {

        String requiredFunction = _args[0];
        String arg1;
        String arg2;
        String arg3;
        String arg4;

        switch(requiredFunction) {
            case "-handshaking":

                _message = new Message("apikey", "localhost", "96D0C1491615C82B9A54D9989779DF825B690748224C2B04F500F370D51827CE2644D8D4A82C18184D73AB8530BB8ED537269603F61DB0D03D2104ABF789970B", "127.0.0.1", "A7BCFA5D490B351BE0754130DF03A068F855DB4333D43921125B9CF2670EF6A40370C646B90401955E1F7BC9CDBF59CE0B2C5467D820BE189C845D0B79CFC96F", "null", "E0A205EC4E7B78BBB56AFF83A733A1BB9FD39D562E67978CC5E7D73B0951DB1954595A20672A63332535E13CC6EC1E1FC8857BB09E0855D7E76E411B6FA16E9D");
                break;

            case "-list_disks":

                _message = new Message("certkey", "list_disks");
                break;

            case "-list_certificates_pfx":

                _message = new Message("pfx", "list_all_certificates");
                break;

            case "-load_key_pfx":

                String[] opts = getOptionsFromFile(_args[1], 5);

                arg1 = opts[1];
                arg2 = opts[2];
                arg3 = opts[3];
                arg4 = opts[4];
                _message = new Message("pfx", "load_key", arg1, arg2, arg3, arg4);
                break;

            case "-create_pkcs7":

                String[] opts_create_pkcs7 = getOptionsFromFile(_args[1], 2);

                String base64_text_create_pkcs7 = Base64Encode(opts_create_pkcs7[1]);

                arg1 = base64_text_create_pkcs7; // getTextFromFile(_args[1], true);
                arg2 = _args[2];
                _message = new Message("pkcs7", "create_pkcs7", arg1, arg2, "no");
                break;

            case "-append_pkcs7_attached":

                String[] opts_append_pkcs7_attached = getOptionsFromFile(_args[1], 2);

                arg1 = opts_append_pkcs7_attached[1]; // getTextFromFile(_args[1], false);
                arg2 = _args[2];
                _message = new Message("pkcs7", "append_pkcs7_attached", arg1, arg2);
                break;

            case "-attach_timestamp_token_pkcs7":

                String[] opts_attach_timestamp_token_pkcs7 = getOptionsFromFile(_args[1], 3);

                arg1 = opts_attach_timestamp_token_pkcs7[1]; //getTextFromFile(_args[1], false);
                arg2 = _args[2];
                arg3 = opts_attach_timestamp_token_pkcs7[2];
                _message = new Message("pkcs7", "attach_timestamp_token_pkcs7", arg1, arg2, arg3);
                break;

            case "-verify_password":

                arg1 = _args[1];
                _message = new Message("pfx", "verify_password", arg1);
                break;

            case "-get_pkcs7_attached_info":

                String[] opts_get_pkcs7_attached_info = getOptionsFromFile(_args[1], 2);

                arg1 = opts_get_pkcs7_attached_info[1];//_args[1];
                _message = new Message("pkcs7", "get_pkcs7_attached_info", arg1, "");
                break;

            case "-get_certificate_chain":

                arg1 = _args[1];
                _message = new Message("x509", "get_certificate_chain", arg1);
                break;

            case "-get_certificate_info":

                arg1 = _args[1];
                _message = new Message("x509", "get_certificate_info", arg1);
                break;

            case "-get_digest_hex":

                bytes = Base64.getEncoder().encode(_args[1].getBytes());
                arg1 = new String(bytes);
                _message = new Message("cryptoauth", "get_digest_hex", arg1);
                break;

            default:

                _message = null;
                break;

        }
    }

    private static String getTextFromFile(String _file_path, boolean _convertToBase64) {

        String text = "";

        try {
            FileInputStream inFile = new FileInputStream(_file_path);
            byte[] str = new byte[inFile.available()];
            inFile.read(str);
            text = new String(str);

            if(_convertToBase64) {
                str = Base64.getEncoder().encode(text.getBytes());
                text = new String(str);
            }

        }
        catch (FileNotFoundException e) {

        }
        catch (IOException e) {

        }

        return text;

    }

    private static String[] getOptionsFromFile(String _file_path, int _n) {

        String[] arr = new String[_n];

        try {
            FileInputStream fis =  new FileInputStream(_file_path);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis, "UTF-8"));

//            BufferedReader br = new BufferedReader(new FileReader(_file_path));
            String line = br.readLine();

            int i = 0;
            while(line != null) {
                if(i >= _n) break;
                arr[i] = line;
                line = br.readLine();
                i++;
            }

        }
        catch (FileNotFoundException e) {

        }
        catch (IOException e) {

        }

        return arr;


    }

    private static String Base64Encode(String _text) {

        byte[] str = Base64.getEncoder().encode(_text.getBytes());

        return new String(str);

    }





}


