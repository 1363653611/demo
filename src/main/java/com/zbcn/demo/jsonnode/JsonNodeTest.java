package com.zbcn.demo.jsonnode;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * JsonNode 测试
 *
 * @author Administrator
 * @date 2018/10/24 11:47
 */
public class JsonNodeTest {

    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        testPutCrmId(mapper);
//        String jsonStr = getStr();
//        testGetGroupId(jsonStr, mapper);
    }

    private static JsonNode testPutCrmId( ObjectMapper mapper){
        String user = user();
        try {
            JsonNode jsonNode = mapper.readTree(user);

            ObjectNode data = (ObjectNode)jsonNode.at("/data");
            data.put("crmId",data.at("/crmUserId").asText());
            data.remove("crmUserId");
            System.out.println(data);
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void testGetGroupId(String jsonStr, ObjectMapper mapper) {
        try {
            JsonNode jsonNode = mapper.readTree(jsonStr);
            //List<JsonNode> values = jsonNode.at("/data").findValues("id");
            JsonNode businessGroup = jsonNode.at("/data");
            List<String> ids = new ArrayList();
            if(businessGroup.isArray()){
                for ( JsonNode data : businessGroup) {
                    String groupId = data.at("/group").at("/id").asText();
                    ids.add(groupId);
                }
            }
            System.out.println(ids);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String getStr() {
        return  "{\"ok\":true,\"errorCode\":\"\",\"errorMsg\":\"\",\"data\":[{\"group\":{\"id\":\"5bcd2fd9f40ab0e41c5319aa\",\"name\":\"测试林芝\",\"parentIds\":[\"5bcd2fc219cbb0e411061df2\"],\"province\":{\"key\":\"540000\",\"val\":\"西藏\"},\"city\":{\"key\":\"540400\",\"val\":\"林芝市\"},\"type\":\"business\"},\"people\":[{\"id\":\"5a6adb841400002e012db74f\",\"name\":\"候艳玲\",\"phone\":\"18801230840\",\"user\":{\"id\":\"5a6adeaf140000bb012db824\",\"username\":\"18801230840\"}},{\"id\":\"5a6adb841400002e012db750\",\"name\":\"闫亚拨超管\",\"phone\":\"18332392618\",\"user\":{\"id\":\"5a6adeae140000bb012db823\",\"username\":\"18332392618\"}},{\"id\":\"5a6adb8414000033012db752\",\"name\":\"韩喜悦\",\"phone\":\"18233150485\",\"user\":{\"id\":\"5a6adeae140000bb012db821\",\"username\":\"18233150485\"}},{\"id\":\"5a6adb851400002e012db754\",\"name\":\"贾春燕\",\"phone\":\"15354617573\",\"user\":{\"id\":\"5a6adead140000bb012db81f\",\"username\":\"15354617573\"}},{\"id\":\"5ade87db46000058002e4ae7\",\"name\":\"段连江\",\"phone\":\"18001077702\",\"user\":{\"id\":\"5ade87db46000058002e4ae8\",\"username\":\"18001077702\"}},{\"id\":\"5b223429150000834909dcde\",\"name\":\"闫亚拨采集2\",\"phone\":\"10000000103\",\"user\":{\"id\":\"5b223429150000834909dcdf\",\"username\":\"10000000103\"}},{\"id\":\"5b223429150000834909dce1\",\"name\":\"韩喜悦销售2\",\"phone\":\"10000000104\",\"user\":{\"id\":\"5b223429150000b14909dce2\",\"username\":\"10000000104\"}},{\"id\":\"5b223429150000b34909dce4\",\"name\":\"yyb采集1\",\"phone\":\"10000000105\",\"user\":{\"id\":\"5b223429150000b34909dce5\",\"username\":\"10000000105\"}},{\"id\":\"5b22342a150000834909dcea\",\"name\":\"yyb销售2\",\"phone\":\"10000000107\",\"user\":{\"id\":\"5b22342a150000834909dceb\",\"username\":\"10000000107\"}},{\"id\":\"5b22342a150000b34909dcf3\",\"name\":\"yyb城市经理\",\"phone\":\"10000000110\",\"user\":{\"id\":\"5b22342a150000b14909dcf4\",\"username\":\"10000000110\"}},{\"id\":\"5b22342a150000b34909dcf6\",\"name\":\"yyb销售主管\",\"phone\":\"10000000111\",\"user\":{\"id\":\"5b22342a150000b94909dcf7\",\"username\":\"10000000111\"}},{\"id\":\"5b22342f150000b14909dd5c\",\"name\":\"txh－交易销售\",\"phone\":\"10000000145\",\"user\":{\"id\":\"5b22342f150000b24909dd5d\",\"username\":\"10000000145\"}},{\"id\":\"5b22342f150000b24909dd5f\",\"name\":\"txh-城市经理\",\"phone\":\"10000000146\",\"user\":{\"id\":\"5b22342f150000b24909dd60\",\"username\":\"10000000146\"}},{\"id\":\"5b22342f150000b94909dd62\",\"name\":\"txh－车审\",\"phone\":\"10000000147\",\"user\":{\"id\":\"5b22342f150000b94909dd63\",\"username\":\"10000000147\"}},{\"id\":\"5b22342f150000b94909dd65\",\"name\":\"运营测试销售\",\"phone\":\"10000000148\",\"user\":{\"id\":\"5b223430150000b14909dd66\",\"username\":\"10000000148\"}},{\"id\":\"5b3b9fce2f0000a212c2dcbd\",\"name\":\"王兴\",\"phone\":\"15201286058\",\"user\":{\"id\":\"5b3b9fce2f00009412c2dcbe\",\"username\":\"15201286058\"}},{\"id\":\"5b3dd0d5160000fd0c04f2e2\",\"name\":\"jiachunyan\",\"phone\":\"17610765420\",\"user\":{\"id\":\"5b3dd0d6160000060d04f2e3\",\"username\":\"17610765420\"}},{\"id\":\"5b5817dc150000101248f787\",\"name\":\"测试156-销售\",\"phone\":\"10000000156\",\"user\":{\"id\":\"5b5817dc150000001248f788\",\"username\":\"10000000156\"}},{\"id\":\"5b58180f1500002f1248f78a\",\"name\":\"测试157-城市经理\",\"phone\":\"10000000157\",\"user\":{\"id\":\"5b58180f150000321248f78b\",\"username\":\"10000000157\"}},{\"id\":\"5b5818731500004b1248f790\",\"name\":\"sys交易销售\",\"phone\":\"10000000159\",\"user\":{\"id\":\"5b5818731500004b1248f791\",\"username\":\"10000000159\"}},{\"id\":\"5b5818a31500006c1248f793\",\"name\":\"sys城市经理\",\"phone\":\"10000000160\",\"user\":{\"id\":\"5b5818a41500006c1248f794\",\"username\":\"10000000160\"}},{\"id\":\"5b582d5b150000501748f79c\",\"name\":\"sys好车无忧金专\",\"phone\":\"10000000161\",\"user\":{\"id\":\"5b582d5c150000501748f79d\",\"username\":\"10000000161\"}},{\"id\":\"5b582e2d150000801748f7a8\",\"name\":\"sys金融\",\"phone\":\"10000000165\",\"user\":{\"id\":\"5b582e2d1500008e1748f7a9\",\"username\":\"10000000165\"}},{\"id\":\"5b582eaa150000a31748f7b1\",\"name\":\"hyl－城市经理\",\"phone\":\"10000000168\",\"user\":{\"id\":\"5b582eaa150000cd1748f7b2\",\"username\":\"10000000168\"}},{\"id\":\"5b582efe150000c11748f7b7\",\"name\":\"交易-jcy\",\"phone\":\"10000000170\",\"user\":{\"id\":\"5b582eff150000c11748f7b8\",\"username\":\"10000000170\"}},{\"id\":\"5b5eaee9130000aa428de802\",\"name\":\"陶晓慧\",\"phone\":\"15715353528\",\"user\":{\"id\":\"5b5eaee9130000ab428de803\",\"username\":\"15715353528\"}},{\"id\":\"5b6901322d0000178d217349\",\"name\":\"李坤\",\"phone\":\"18500368339\",\"user\":{\"id\":\"5b6901322d0000178d21734a\",\"username\":\"18500368339\"}},{\"id\":\"5b694c662d0000bf9d21735e\",\"name\":\"城市经理-jcy\",\"phone\":\"10000000172\",\"user\":{\"id\":\"5b694c662d0000bf9d21735f\",\"username\":\"10000000172\"}},{\"id\":\"5b6cf4b32d000025f32173a0\",\"name\":\"sys车车网金专\",\"phone\":\"10000000185\",\"user\":{\"id\":\"5b6cf4b42d000030f32173a1\",\"username\":\"10000000185\"}},{\"id\":\"5b6d2f762d0000b2fb2173b4\",\"name\":\"牛群\",\"phone\":\"17689551930\",\"user\":{\"id\":\"5b6d2f762d0000c3fb2173b5\",\"username\":\"17689551930\"}},{\"id\":\"5b9859623000004324c38db1\",\"name\":\"宗举\",\"phone\":\"17600149608\",\"user\":{\"id\":\"5b9859623000004224c38db2\",\"username\":\"17600149608\"}},{\"id\":\"5ba33f203000000c08c392ae\",\"name\":\"组织-销售\",\"phone\":\"10000000200\",\"user\":{\"id\":\"5ba33f203000002408c392af\",\"username\":\"10000000200\"}},{\"id\":\"5ba342aa3000006609c392b4\",\"name\":\"组织-城市经理\",\"phone\":\"10000000300\",\"user\":{\"id\":\"5ba342aa3000006609c392b5\",\"username\":\"10000000300\"}},{\"id\":\"5ba344a3300000250ac392ba\",\"name\":\"hxy收单金融销售\",\"phone\":\"10000000302\",\"user\":{\"id\":\"5ba344a33000002d0ac392bb\",\"username\":\"10000000302\"}},{\"id\":\"5ba344fd300000640ac392bd\",\"name\":\"金融销售管理hxy\",\"phone\":\"10000000303\",\"user\":{\"id\":\"5ba344fd300000480ac392be\",\"username\":\"10000000303\"}},{\"id\":\"5ba34511300000780ac392c0\",\"name\":\"金融产品管理员hxy\",\"phone\":\"10000000304\",\"user\":{\"id\":\"5ba34511300000770ac392c1\",\"username\":\"10000000304\"}},{\"id\":\"5ba34538300000770ac392c6\",\"name\":\"yyb金融二期销售\",\"phone\":\"10000000306\",\"user\":{\"id\":\"5ba34538300000770ac392c7\",\"username\":\"10000000306\"}},{\"id\":\"5ba3454c300000930ac392c9\",\"name\":\"yyb金融二期管理\",\"phone\":\"10000000307\",\"user\":{\"id\":\"5ba3454c3000009d0ac392ca\",\"username\":\"10000000307\"}},{\"id\":\"5ba3455f300000a30ac392cc\",\"name\":\"yyb金融二期产品\",\"phone\":\"10000000308\",\"user\":{\"id\":\"5ba34560300000870ac392cd\",\"username\":\"10000000308\"}},{\"id\":\"5ba34573300000ad0ac392cf\",\"name\":\"sys收单金融\",\"phone\":\"10000000309\",\"user\":{\"id\":\"5ba34573300000a00ac392d0\",\"username\":\"10000000309\"}},{\"id\":\"5ba34587300000b40ac392d2\",\"name\":\"sys金融产品管理\",\"phone\":\"10000000310\",\"user\":{\"id\":\"5ba34587300000a00ac392d3\",\"username\":\"10000000310\"}},{\"id\":\"5ba345be300000a00ac392d5\",\"name\":\"sys金融销售\",\"phone\":\"10000000311\",\"user\":{\"id\":\"5ba345be300000c90ac392d6\",\"username\":\"10000000311\"}},{\"id\":\"5ba3462e3000000e0bc392d8\",\"name\":\"lpp金融产品管理\",\"phone\":\"10000000312\",\"user\":{\"id\":\"5ba3462e3000001b0bc392d9\",\"username\":\"10000000312\"}},{\"id\":\"5ba34642300000250bc392db\",\"name\":\"lpp金融销售管理\",\"phone\":\"10000000313\",\"user\":{\"id\":\"5ba34642300000210bc392dc\",\"username\":\"10000000313\"}},{\"id\":\"5ba34656300000290bc392de\",\"name\":\"lpp收单金融\",\"phone\":\"10000000314\",\"user\":{\"id\":\"5ba34656300000240bc392df\",\"username\":\"10000000314\"}},{\"id\":\"5ba347d5300000a60bc392f9\",\"name\":\"lpp交易销售\",\"phone\":\"10000000323\",\"user\":{\"id\":\"5ba347d5300000d10bc392fa\",\"username\":\"10000000323\"}},{\"id\":\"5ba347e9300000e30bc392fc\",\"name\":\"lpp城市经理\",\"phone\":\"10000000324\",\"user\":{\"id\":\"5ba347e9300000e30bc392fd\",\"username\":\"10000000324\"}},{\"id\":\"5ba347fc300000f60bc392ff\",\"name\":\"hxy金融销售勿动\",\"phone\":\"10000000325\",\"user\":{\"id\":\"5ba347fc300000f70bc39300\",\"username\":\"10000000325\"}},{\"id\":\"5ba34810300000d50bc39302\",\"name\":\"hxy金融管理勿动\",\"phone\":\"10000000326\",\"user\":{\"id\":\"5ba34810300000d50bc39303\",\"username\":\"10000000326\"}},{\"id\":\"5ba34824300000060cc39305\",\"name\":\"sys好车无忧总部\",\"phone\":\"10000000327\",\"user\":{\"id\":\"5ba34824300000060cc39306\",\"username\":\"10000000327\"}},{\"id\":\"5ba34838300000fe0bc39308\",\"name\":\"sys好车无忧金专1\",\"phone\":\"10000000328\",\"user\":{\"id\":\"5ba348383000001b0cc39309\",\"username\":\"10000000328\"}},{\"id\":\"5ba3484b300000090cc3930b\",\"name\":\"lpp快捷金专\",\"phone\":\"10000000329\",\"user\":{\"id\":\"5ba3484b300000380cc3930c\",\"username\":\"10000000329\"}},{\"id\":\"5ba3485f3000004f0cc3930e\",\"name\":\"lpp无忧金专\",\"phone\":\"10000000330\",\"user\":{\"id\":\"5ba3485f300000440cc3930f\",\"username\":\"10000000330\"}},{\"id\":\"5ba34a06300000720dc39329\",\"name\":\"jcy--金融\",\"phone\":\"10000000339\",\"user\":{\"id\":\"5ba34a06300000720dc3932a\",\"username\":\"10000000339\"}}]},{\"group\":{\"id\":\"5bcd3043d2aab0e455d4b3f7\",\"name\":\"测试专用\",\"parentIds\":[\"5bcd2fc219cbb0e411061df2\"],\"province\":{\"key\":\"110000\",\"val\":\"北京市\"},\"city\":{\"key\":\"110100\",\"val\":\"北京\"},\"type\":\"business\"},\"people\":[]}]}";
    }

    public static String user(){
        return "{\"ok\":true,\"errorCode\":\"\",\"errorMsg\":\"\",\"data\":{\"id\":\"5b58180f150000321248f78b\",\"username\":\"10000000157\",\"scopes\":[\"app_download\",\"app_xiaomada_android\",\"app_xiaomada_ios\",\"app_bss\"],\"groups\":[{\"id\":\"5970bca3130000225ce66442\",\"name\":\"测试林芝\",\"state\":\"enable\",\"type\":\"business\",\"isRoot\":null,\"parents\":null,\"province\":null,\"city\":null,\"area\":null,\"market\":null}],\"businessGroup\":{\"id\":\"5970bca3130000225ce66442\",\"name\":\"测试林芝\",\"state\":\"enable\",\"type\":\"business\",\"province\":{\"key\":\"540000\",\"val\":\"西藏\"},\"city\":{\"key\":\"540400\",\"val\":\"林芝市\"},\"isRoot\":null,\"parents\":null,\"area\":null,\"market\":null},\"person\":{\"id\":\"9715fd72ec1f4ddbbb550d9bf80802c1\",\"name\":\"测试157-城市经理\",\"phone\":\"10000000157\"},\"roles\":[{\"id\":\"584b9c0e3d0000e50051db4e\",\"title\":\"城市销售\"},{\"id\":\"584f5ba63e000012033f1c83\",\"title\":\"基础权限\"},{\"id\":\"5948d702410000e91b5c3d5a\",\"title\":\"交易销售\"},{\"id\":\"59edaa842c00000f98b30926\",\"title\":\"城市经理（交易）\"},{\"id\":\"59f15b5d120000b018829a89\",\"title\":\"城市运营（交易）\"},{\"id\":\"5a0bc9702e0000088cfb6c7b\",\"title\":\"城市门店审核\"}],\"devices\":[],\"isEnabled\":true,\"isLocked\":false,\"createAt\":\"2018-07-25T00:00:00.000+08:00\",\"crmAccountName\":\"wb10000000157\",\"crmUserId\":\"9715fd72ec1f4ddbbb550d9bf80802c1\",\"password\":null,\"nickname\":null,\"creatorId\":null,\"creatorName\":null,\"creatorPhone\":null,\"crmGroupId\":null,\"link\":null}}";
    }

    public static String getId(){
        return "";
    }

}
