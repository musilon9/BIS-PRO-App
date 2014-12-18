package app.com.bisnode.tabfragments.company;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import app.com.bisnode.R;
import app.com.bisnode.fakedata.FakeSearch;
import app.com.bisnode.objects.Company;
import app.com.bisnode.onclicklisteners.DatabaseHandler;
import app.com.bisnode.requests.CustomJsonArrayRequest;
import app.com.bisnode.tabfragments.PlaceHolderFragment;

public class ContactsFragment extends PlaceHolderFragment {

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ContactsFragment newInstance(int sectionNumber) {
        ContactsFragment fragment = new ContactsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public ContactsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_contacts, container, false);
        Company com = FakeSearch.getExample();
        setListeners(com, rootView);
        setContents(rootView, com);
        return rootView;
    }

    private void setContents(View v, Company com) {
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        setBasicContent(v, queue);
        setAddressContent(v, queue);
        setContactContents(v, queue);
//        setPhoneContent(v, com);
//        setEmailContent(v, com);
//        setWebContent(v, com);
    }

    private void setBasicContent(View v, RequestQueue queue) {
        TextView companyName = (TextView) v.findViewById(R.id.companyName);
        companyName.setText(getActivity().getIntent().getStringExtra("name"));
        TextView companyICO = (TextView) v.findViewById(R.id.companyICO);
        TextView companyDIC = (TextView) v.findViewById(R.id.companyDIC);
        TextView companyType = (TextView) v.findViewById(R.id.companyType);
        TextView companyDPH = (TextView) v.findViewById(R.id.companyDPH);
        requestCompanyID(companyICO, companyDIC, queue);
        requestCompanyType(companyType, companyDPH, queue);
    }

    private void setAddressContent(View rootView, RequestQueue queue) {
        LinearLayout block = (LinearLayout) rootView.findViewById(R.id.addressBlock);
        TextView title = (TextView) block.findViewById(R.id.title_blockContact);
        title.setText(getString(R.string.address_sectionTitle));
        ImageButton icon = (ImageButton) block.findViewById(R.id.contact_imgButton);
        icon.setImageResource(R.drawable.ic_map);
        TextView address = (TextView) block.findViewById(R.id.info_blockContact);
        requestAddress(address, queue);

        // start Google Maps to show directions TODO must be async, add to requestAddress!
//        icon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String uri = "http://maps.google.com/maps?daddr=" + com.getAddress() + ",+" + com.getCity();
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
//                getActivity().startActivity(intent);
//            }
//        });
    }

    private void setPhoneContent(View rootView, final Company com) {
        LinearLayout block = (LinearLayout) rootView.findViewById(R.id.phoneBlock);
        TextView title = (TextView) block.findViewById(R.id.title_blockContact);
        title.setText(getString(R.string.phone_sectionTitle));
        ImageButton icon = (ImageButton) block.findViewById(R.id.contact_imgButton);
        icon.setImageResource(R.drawable.ic_phone);
        TextView numbers = (TextView) block.findViewById(R.id.info_blockContact);
        String phoneNumbers = "";
        ArrayList<String> numb = com.getPhoneNumbers();
        for (int i = 0; i < numb.size(); i++) {
            phoneNumbers += numb.get(i) + (i == numb.size() - 1 ? "" : "\n");
        }
        numbers.setText(phoneNumbers);

//        icon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String uri = String.format("tel:%s", com.getPhoneNumbers().get(0));
//                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(uri));
//                getActivity().startActivity(intent);
//            }
//        });
    }

    private void setEmailContent(View rootView, final Company com) {
        LinearLayout block = (LinearLayout) rootView.findViewById(R.id.emailBlock);
        TextView title = (TextView) block.findViewById(R.id.title_blockContact);
        title.setText(getString(R.string.email_sectionTitle));
        ImageButton icon = (ImageButton) block.findViewById(R.id.contact_imgButton);
        icon.setImageResource(R.drawable.ic_email);
        TextView emails = (TextView) block.findViewById(R.id.info_blockContact);
        String addresses = "";
        ArrayList<String> add = com.getEmails();
        for (int i = 0; i < add.size(); i++) {
           addresses += add.get(i) + (i == add.size() - 1 ? "" : "\n");
        }
        emails.setText(addresses);

//        icon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String uri = String.format("mailto:%s", com.getEmails().get(0));
//                Intent intent = new Intent(Intent.ACTION_SEND, Uri.parse(uri));
//                getActivity().startActivity(intent);
//            }
//        });
    }

    private void setWebContent(View rootView, final Company com) {
        LinearLayout block = (LinearLayout) rootView.findViewById(R.id.webBlock);
        TextView title = (TextView) block.findViewById(R.id.title_blockContact);
        title.setText(getString(R.string.web_sectionTitle));
        ImageButton icon = (ImageButton) block.findViewById(R.id.contact_imgButton);
        icon.setImageResource(R.drawable.ic_web);
        TextView web = (TextView) block.findViewById(R.id.info_blockContact);
        web.setText(com.getWebAddress());

//        icon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String uri = com.getWebAddress();
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
//                getActivity().startActivity(intent);
//            }
//        });
    }

    private void setContactContents(View rootView, RequestQueue queue) {
        // PHONE NUMBER
        LinearLayout block = (LinearLayout) rootView.findViewById(R.id.phoneBlock);
        TextView title = (TextView) block.findViewById(R.id.title_blockContact);
        ImageButton icon = (ImageButton) block.findViewById(R.id.contact_imgButton);
        title.setText(getString(R.string.phone_sectionTitle));
        icon.setImageResource(R.drawable.ic_phone);
        TextView phoneView = (TextView) block.findViewById(R.id.info_blockContact);
        // E-MAIL ADDRESS
        block = (LinearLayout) rootView.findViewById(R.id.emailBlock);
        title = (TextView) block.findViewById(R.id.title_blockContact);
        icon = (ImageButton) block.findViewById(R.id.contact_imgButton);
        title.setText(getString(R.string.email_sectionTitle));
        icon.setImageResource(R.drawable.ic_email);
        TextView emailView = (TextView) block.findViewById(R.id.info_blockContact);
        // WEB ADDRESS
        block = (LinearLayout) rootView.findViewById(R.id.webBlock);
        title = (TextView) block.findViewById(R.id.title_blockContact);
        icon = (ImageButton) block.findViewById(R.id.contact_imgButton);
        title.setText(getString(R.string.web_sectionTitle));
        icon.setImageResource(R.drawable.ic_web);
        TextView webView = (TextView) block.findViewById(R.id.info_blockContact);

        requestContacts(phoneView, emailView, webView, queue);
    }

    private void requestCompanyID(final TextView ICO, final TextView DIC, RequestQueue queue) {
        String url = String.format(getString(R.string.requestID),
                getActivity().getIntent().getLongExtra("id", 0));
        CustomJsonArrayRequest request = new CustomJsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject contact = response.getJSONObject(i);
                        String s = contact.optString(getString(R.string.jsonFieldType));
                        if (s.equals(getString(R.string.jsonIdICO))) {
                            ICO.setText(String.format("%s: %s", getString(R.string.jsonIdICO),
                                    contact.optString(getString(R.string.jsonFieldValue))));
                        } else if (s.equals(getString(R.string.jsonIdDIC))) {
                            DIC.setText(String.format("%s: %s", getString(R.string.jsonIdDIC),
                                    contact.optString(getString(R.string.jsonFieldValue))));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), getString(R.string.serverError), Toast.LENGTH_LONG).show();
            }
        });
        queue.add(request);
    }

    private void requestCompanyType(final TextView companyType, final TextView companyDPH, RequestQueue queue) {
        String url = String.format(getString(R.string.requestRegInfo),
                getActivity().getIntent().getLongExtra("id", 0));
        CustomJsonArrayRequest request = new CustomJsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject contact = response.getJSONObject(i);
                        String s = contact.optString(getString(R.string.jsonFieldType));
                        if (s.equals(getString(R.string.jsonIdCompType))) {
                            companyType.setText(String.format("%s %s %s", contact.optString(getString(R.string.jsonFieldValue)),
                                    getString(R.string.labelSince),
                                    contact.optString(getString(R.string.jsonFieldStartDate)).substring(0, 4)));
                        } else if (s.equals(getString(R.string.jsonIdCompDPH))) {
                            companyDPH.setText(contact.optString(getString(R.string.jsonFieldValue)));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), getString(R.string.serverError), Toast.LENGTH_LONG).show();
            }
        });
        queue.add(request);
    }

    private void requestAddress(final TextView addressView, RequestQueue queue) {
        String url = String.format(
                getString(R.string.requestAddresses),
                getActivity().getIntent().getLongExtra("id", 0));
        CustomJsonArrayRequest request = new CustomJsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject jsonAddress = response.getJSONObject(0);
                    addressView.setText(jsonAddress.optString(getString(R.string.jsonFieldValue)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), getString(R.string.serverError), Toast.LENGTH_LONG).show();
            }
        });
        queue.add(request);
    }

    private void requestContacts(final TextView phoneView, final TextView emailView,
                                 final TextView webView, RequestQueue queue) {
        String url = String.format(getString(R.string.requestContacts),
                getActivity().getIntent().getLongExtra("id", 0));
        CustomJsonArrayRequest request = new CustomJsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                String address = "";
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject contact = response.getJSONObject(i);
                        String s = contact.optString(getString(R.string.jsonFieldType));
                        if (s.equals(getString(R.string.jsonContactPhone))) {
                            phoneView.setText(contact.optString(getString(R.string.jsonFieldValue)));
                        } else if (s.equals(getString(R.string.jsonContactEmail))) {
                            address = contact.optString(getString(R.string.jsonFieldValue));
                            emailView.setText(address.substring(3, address.length() - 1));
                        } else if (s.equals(getString(R.string.jsonContactWeb))) {
                            address = contact.optString(getString(R.string.jsonFieldValue));
                            webView.setText(address.substring(3, address.length() - 1));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), getString(R.string.serverError), Toast.LENGTH_LONG).show();
            }
        });
        queue.add(request);
    }

    public void setListeners(Company com, View rootView) {
        ImageButton imageView = (ImageButton) rootView.findViewById(R.id.mapIcon);
        imageView.setClickable(true);
        DatabaseHandler databaseHandler = new DatabaseHandler(this.getActivity().getApplicationContext(), com);
        imageView.setOnClickListener(databaseHandler);
        databaseHandler.insertCompanyAsHistory(com);
    }
}
