package com.example.kp2101.journey0100;



import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;


/**
 * Created by TonyLabNew on 2016/1/8.
 */
public class transActivity extends AppCompatActivity {
    String jIdFrom;
    String uIdTo;
    String uNameTo;
    String uIdFrom;
    String uNameFrom;


    private static final String TAG = "stickynotes";
    private boolean mResumed = false;
    private boolean mWriteMode = false;
    NfcAdapter mNfcAdapter;
    EditText mNote;
    TextView tvReceived;
    PendingIntent mNfcPendingIntent;
    IntentFilter[] mWriteTagFilters;
    IntentFilter[] mNdefExchangeFilters;
    Button btnPay, btnEdit, btnReceive;
    GlobalVariable globalVariable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trans_main);

        //抓全域變數
        globalVariable = (GlobalVariable)getApplicationContext();
        uIdFrom=globalVariable.uId;
        jIdFrom=globalVariable.jId;
        //Log.d("jIdFrom",jIdFrom);

        //抓上一個activity傳來的uIdTo
        uIdTo = getIntent().getExtras().getString("uIdTo");



        //Set Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("交易 > " + uNameTo);



        //NFC
        mNote = ((EditText) findViewById(R.id.note));

        tvReceived = (TextView) findViewById(R.id.tvRecieved);
        tvReceived.setVisibility(View.INVISIBLE);
        btnPay = (Button) findViewById(R.id.btnPay);
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNote.setEnabled(false);
                enableTagWriteMode();
            }
        });
        btnEdit = (Button) findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNote.setEnabled(true);
                disableTagWriteMode();
            }
        });

        btnReceive = (Button) findViewById(R.id.btnReceive);
        btnReceive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWriteMode = true;
                mNote.setEnabled(false);
            }
        });

        mNote.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mResumed) {
                    mNfcAdapter.enableForegroundNdefPush(transActivity.this, getNoteAsNdef());
                }
            }
        });

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        // Handle all of our received NFC intents in this activity.
        // 在send intent到activity之前Android會先fill這個intent
        //產生一個PendingIntent交給mNFCAdapter，請它在發現符合intent filter條件的NFC tag靠近時，
        // 代表本activity送出一個intent去啟動MainActivity.class
        //ie: 用getClass()來處理該tag，如果該class已經在top(例如foreground)，就不要再啟動它，ie不要呼叫onCreate()而是onNewIntent()
        mNfcPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        // Intent filters for exchanging over p2p.

        // 用來讀從tag或手機來的 note的 Intent filters
        IntentFilter ndefDetected = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        try {
            ndefDetected.addDataType("text/plain");
        }
        catch (IntentFilter.MalformedMimeTypeException e) { }

        mNdefExchangeFilters = new IntentFilter[] { ndefDetected };

        // 用來寫東西到tag的 Intent filters
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        mWriteTagFilters = new IntentFilter[] { tagDetected };



    }




    @Override
    protected void onResume() {
        super.onResume();
        mResumed = true;
        // Sticky notes received from Android
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
            NdefMessage[] messages = getNdefMessages(getIntent());
            byte[] payload = messages[0].getRecords()[0].getPayload();
            byte[] payload1 = messages[0].getRecords()[1].getPayload();
            byte[] payload2 = messages[0].getRecords()[2].getPayload();
            byte[] payload3 = messages[0].getRecords()[3].getPayload();
            setNoteBody(new String(payload), new String(payload1), new String(payload2), new String(payload3));
            setIntent(new Intent()); // Consume this intent.
        }
        enableNdefExchangeMode();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mResumed = false;
        mNfcAdapter.disableForegroundNdefPush(this);
        //本功能暫停的時候要把foreground dispatch的權限disable才不會影響其他NFC app運作

    }

    @Override
    protected void onNewIntent(Intent intent) {
        //Log.d("onNewIntent", String.valueOf(mWriteMode));
        // NDEF exchange mode
        if (mWriteMode && NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
            //Log.d("NDEF exchange mode", String.valueOf(mWriteMode));
            NdefMessage[] msgs = getNdefMessages(intent);
            promptForContent(msgs[0]);

        }

        // Tag writing mode
        if (mWriteMode && NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
            //Log.d("Tag writing mode", String.valueOf(mWriteMode));
            Tag detectedTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            writeTag(getNoteAsNdef(), detectedTag);
        }
    }


//    private View.OnClickListener mTagWriter = new View.OnClickListener() {
//        @Override
//        public void onClick(View arg0) {
//            // Write to a tag for as long as the dialog is shown.
//            disableNdefExchangeMode();
////            enableTagWriteMode();
//            Log.i("mTagWriter", "!!!!!!!");
//            new AlertDialog.Builder(MainActivity.this).setTitle("Touch tag to write")
//                    .setOnCancelListener(new DialogInterface.OnCancelListener() {
//                        @Override
//                        public void onCancel(DialogInterface dialog) {
//                            disableTagWriteMode();
//                            enableNdefExchangeMode();
//                        }
//                    }).create().show();
//        }
//    };

    private void promptForContent(final NdefMessage msg) {
        final String receivejIdFrom = new String(msg.getRecords()[0].getPayload());
        final String receiveuIdFrom = new String(msg.getRecords()[1].getPayload());
        final String receiveuIdTo = new String(msg.getRecords()[2].getPayload());
        final String receiveMoney = new String(msg.getRecords()[3].getPayload());



        //判斷接收者是否為交易對象
        Toast.makeText(this,"receiveuIdTo="+receiveuIdTo , Toast.LENGTH_SHORT).show();
        Toast.makeText(this,"globalVariable.uId="+globalVariable.uId , Toast.LENGTH_SHORT).show();

        if(receiveuIdTo.equals(globalVariable.uId) && receivejIdFrom.equals(globalVariable.jId)) {
            new AlertDialog.Builder(this).setTitle("從" + receiveuIdTo + "收到金額 " + receiveMoney + "\n是否確認")
                    .setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            String body = new String(msg.getRecords()[0].getPayload());
                            setNoteBody(receivejIdFrom, receiveuIdFrom,receiveuIdTo, receiveMoney);
                        }
                    })
                    .setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {

                        }
                    }).show();
        }else{

            new AlertDialog.Builder(this).setTitle("交易失敗").show();
        }
    }

    private void setNoteBody(String jIdFrom, String uIdFrom,String uIdTo, String money) {
        //Editable text = mNote.getText();
        //text.clear();
        //text.append(body);
        tvReceived.setText(uIdFrom+ " 給 " + uIdTo + " $" + money);
        tvReceived.setVisibility(View.VISIBLE);
        mNote.setEnabled(true);

        TransDB.addTrans(jIdFrom,uIdFrom,uIdTo,money);


    }

    //要傳的訊息放這
    private NdefMessage getNoteAsNdef() {

        byte[] txtBjIdFrom = jIdFrom.getBytes();
        byte[] txtBuIdFrom = uIdFrom.getBytes();
        byte[] txtBuIdTo = uIdTo.getBytes();
        byte[] txtBMoney = mNote.getText().toString().getBytes();
        NdefRecord txtRjIdFrom = new NdefRecord(NdefRecord.TNF_MIME_MEDIA, "text/plain".getBytes(), new byte[] {}, txtBjIdFrom);
        NdefRecord txtRuIdFrom = new NdefRecord(NdefRecord.TNF_MIME_MEDIA, "text/plain".getBytes(), new byte[] {}, txtBuIdFrom);
        NdefRecord txtRuIdTo = new NdefRecord(NdefRecord.TNF_MIME_MEDIA, "text/plain".getBytes(), new byte[] {}, txtBuIdTo);
        NdefRecord txtRMoney = new NdefRecord(NdefRecord.TNF_MIME_MEDIA, "text/plain".getBytes(), new byte[] {}, txtBMoney);
        return new NdefMessage(new NdefRecord[] {txtRjIdFrom, txtRuIdFrom,txtRuIdTo,txtRMoney});
    }

    NdefMessage[] getNdefMessages(Intent intent) {
        // Parse the intent
        NdefMessage[] msgs = null;
        String action = intent.getAction();
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action) || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            if (rawMsgs != null) {
                msgs = new NdefMessage[rawMsgs.length];
                for (int i = 0; i < rawMsgs.length; i++) {
                    msgs[i] = (NdefMessage) rawMsgs[i];
                    //Log.d("msgs[i]", msgs[i].toString());
                }
            } else {
                // Unknown tag type
                byte[] empty = new byte[] {};
                NdefRecord record = new NdefRecord(NdefRecord.TNF_UNKNOWN, empty, empty, empty);
                NdefMessage msg = new NdefMessage(new NdefRecord[] {record});
                msgs = new NdefMessage[] {msg};
            }
        } else {
           // Log.d(TAG, "Unknown intent.");
            finish();
        }
        return msgs;
    }

    //允許用手機用NFC來進行p2p交換資料
    private void enableNdefExchangeMode() {
        mNfcAdapter.enableForegroundNdefPush(transActivity.this, getNoteAsNdef());
        mNfcAdapter.enableForegroundDispatch(this, mNfcPendingIntent, mNdefExchangeFilters, null);
    }

    private void disableNdefExchangeMode() {
        mNfcAdapter.disableForegroundNdefPush(this);
        mNfcAdapter.disableForegroundDispatch(this);
    }

    private void enableTagWriteMode() {
        //Log.d("enableTagWriteMode", String.valueOf(mWriteMode));
        mWriteMode = true;
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        mWriteTagFilters = new IntentFilter[] { tagDetected };
        mNfcAdapter.enableForegroundDispatch(this, mNfcPendingIntent, mWriteTagFilters, null);
    }

    private void disableTagWriteMode() {
        //Log.d("disableTagWriteMode", String.valueOf(mWriteMode));
        mWriteMode = false;
        mNfcAdapter.disableForegroundDispatch(this);
    }

    boolean writeTag(NdefMessage message, Tag tag) {
        int size = message.toByteArray().length;
        try {
            Ndef ndef = Ndef.get(tag);
            if (ndef != null) {
                ndef.connect();
                if (!ndef.isWritable()) {
                    toast("Tag is read-only.");
                    return false;
                }
                if (ndef.getMaxSize() < size) {
                    toast("Tag capacity is " + ndef.getMaxSize() + " bytes, message is " + size + " bytes.");
                    return false;
                }
                ndef.writeNdefMessage(message);
                toast("Wrote message to pre-formatted tag.");
                return true;
            } else {
                NdefFormatable format = NdefFormatable.get(tag);
                if (format != null) {
                    try {
                        format.connect();
                        format.format(message);
                        toast("Formatted tag and wrote message");
                        return true;
                    } catch (IOException e) {
                        toast("Failed to format tag.");
                        return false;
                    }
                } else {
                    toast("Tag doesn't support NDEF.");
                    return false;
                }
            }
        } catch (Exception e) {
            toast("Failed to write tag");
            toast("又不是你傳，亂按什麼");

        }

        return false;
    }

    private void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }



}
