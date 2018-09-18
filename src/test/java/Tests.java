import com.google.gson.Gson;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import org.rotaracturuguay.eraupay.util.CardContents;
import org.rotaracturuguay.eraupay.util.CardProcessor;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Tests {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final String clave = "putoelqueleeputoelqueleeputoelquelee";

    public static void main(String[] args) {
        CardContents c = CardContents.build()
                .setIdCard(123456789L)
                .setIdUser(2556L)
                .setBalance(28.65D)
                .setCreationTimestamp(new Timestamp(System.currentTimeMillis()).getTime());

        //JUST TO SEE THE STRING
        Gson gson = new Gson();
        System.out.println(gson.toJson(c));


        //FIRST BUILD A PROCESSOR WITH THE KEY
        CardProcessor cp = CardProcessor.build().setKey(clave);

        try {
            //WRITE PHASE
            String s = cp.getWritablePayload(c);
            System.out.println(s);
            //s HOLDS THE STRING TO BE WRITTEN TO CARD

            //READ PHASE
            //FIRST VERIFY, THEN BUILD FROM STRING
            if (cp.isPayloadValid(s)) {
                CardContents cc = cp.getContentsFromPayload(s);
                System.out.println(cc.getIdCard());
                System.out.println(cc.getIdUser());
                System.out.println(cc.getCreationTimestamp());
                System.out.println(cc.getBalance());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
