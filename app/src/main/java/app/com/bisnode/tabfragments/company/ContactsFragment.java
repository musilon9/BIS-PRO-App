package app.com.bisnode.tabfragments.company;


import android.content.Intent;
import android.net.Uri;
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

import app.com.bisnode.R;
import app.com.bisnode.adapters.CompanyModel;
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
        appendFavouriteListener(rootView);
        displayContents(rootView);
        return rootView;
    }

    private void displayContents(View v) {
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        displayRegistrationInfo(v, queue);
        displayAddress(v, queue);
        displayContacts(v, queue);
    }

    private void displayRegistrationInfo(View v, RequestQueue queue) {
        TextView companyName = (TextView) v.findViewById(R.id.companyName);
        companyName.setText(getActivity().getIntent().getStringExtra("name"));
        TextView companyICO = (TextView) v.findViewById(R.id.companyICO);
        TextView companyDIC = (TextView) v.findViewById(R.id.companyDIC);
        TextView companyType = (TextView) v.findViewById(R.id.companyType);
        TextView companyDPH = (TextView) v.findViewById(R.id.companyDPH);
        requestCompanyID(companyICO, companyDIC, queue);
        requestCompanyType(companyType, companyDPH, queue);
    }

    private void displayAddress(View rootView, RequestQueue queue) {
        LinearLayout block = (LinearLayout) rootView.findViewById(R.id.addressBlock);
        TextView title = (TextView) block.findViewById(R.id.title_blockContact);
        title.setText(getString(R.string.address_sectionTitle));
        ImageButton icon = (ImageButton) block.findViewById(R.id.contact_imgButton);
        icon.setImageResource(R.drawable.ic_map);
        TextView address = (TextView) block.findViewById(R.id.info_blockContact);
        requestAddress(address, block, icon, queue);
    }

    private void displayContacts(View rootView, RequestQueue queue) {
        // PHONE NUMBER
        LinearLayout phoneBlock = (LinearLayout) rootView.findViewById(R.id.phoneBlock);
        ImageButton phoneIcon = (ImageButton) phoneBlock.findViewById(R.id.contact_imgButton);
        phoneIcon.setImageResource(R.drawable.ic_phone);
        TextView title = (TextView) phoneBlock.findViewById(R.id.title_blockContact);
        title.setText(getString(R.string.phone_sectionTitle));
        TextView phoneView = (TextView) phoneBlock.findViewById(R.id.info_blockContact);
        // E-MAIL ADDRESS
        LinearLayout emailBlock = (LinearLayout) rootView.findViewById(R.id.emailBlock);
        ImageButton emailIcon = (ImageButton) emailBlock.findViewById(R.id.contact_imgButton);
        emailIcon.setImageResource(R.drawable.ic_email);
        title = (TextView) emailBlock.findViewById(R.id.title_blockContact);
        title.setText(getString(R.string.email_sectionTitle));
        TextView emailView = (TextView) emailBlock.findViewById(R.id.info_blockContact);
        // WEB ADDRESS
        LinearLayout webBlock = (LinearLayout) rootView.findViewById(R.id.webBlock);
        ImageButton webIcon = (ImageButton) webBlock.findViewById(R.id.contact_imgButton);
        webIcon.setImageResource(R.drawable.ic_web);
        title = (TextView) webBlock.findViewById(R.id.title_blockContact);
        title.setText(getString(R.string.web_sectionTitle));
        TextView webView = (TextView) webBlock.findViewById(R.id.info_blockContact);

        requestContacts(phoneView, emailView, webView, phoneBlock, emailBlock, webBlock,
                phoneIcon, emailIcon, webIcon, queue);
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
                            ICO.setVisibility(View.VISIBLE);
                        } else if (s.equals(getString(R.string.jsonIdDIC))) {
                            DIC.setText(String.format("%s: %s", getString(R.string.jsonIdDIC),
                                    contact.optString(getString(R.string.jsonFieldValue))));
                            DIC.setVisibility(View.VISIBLE);
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
                            companyType.setText(String.format("%s (%s %s)", contact.optString(getString(R.string.jsonFieldValue)),
                                    getString(R.string.labelSince),
                                    contact.optString(getString(R.string.jsonFieldStartDate)).substring(0, 4)));
                            companyType.setVisibility(View.VISIBLE);
                        } else if (s.equals(getString(R.string.jsonIdCompDPH))) {
                            companyDPH.setText(contact.optString(getString(R.string.jsonFieldValue)));
                            companyDPH.setVisibility(View.VISIBLE);
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

    private void requestAddress(final TextView addressView, final LinearLayout addressBlock, final ImageButton mapIcon, RequestQueue queue) {
        String url = String.format(
                getString(R.string.requestAddresses),
                getActivity().getIntent().getLongExtra("id", 0));
        CustomJsonArrayRequest request = new CustomJsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject jsonAddress = response.getJSONObject(0);
                    final String value = jsonAddress.optString(getString(R.string.jsonFieldValue));
                    String[] lines = value.split(", ");
                    String addressToDisplay = lines[0] + "\n" + lines[1] + (lines.length > 3 ? ", " + lines[2] : "");
                    if (! lines[lines.length - 1].equals(getString(R.string.czechRepublic)))
                        addressToDisplay += "\n" + lines[lines.length - 1];
                    addressView.setText(addressToDisplay);
                    addressBlock.setVisibility(View.VISIBLE);
                    mapIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String uri = "http://maps.google.com/maps?daddr=" + Uri.encode(value);
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                            getActivity().startActivity(intent);
                        }
                    });
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

    private void requestContacts(final TextView phoneView, final TextView emailView, final TextView webView,
                                 final LinearLayout phoneBlock, final LinearLayout emailBlock, final LinearLayout webBlock,
                                 final ImageButton phoneIcon, final ImageButton emailIcon, final ImageButton webIcon,
                                 RequestQueue queue) {
        String url = String.format(getString(R.string.requestContacts),
                getActivity().getIntent().getLongExtra("id", 0));
        CustomJsonArrayRequest request = new CustomJsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject contact = response.getJSONObject(i);
                        String type = contact.optString(getString(R.string.jsonFieldType));
                        if (type.equals(getString(R.string.jsonContactPhone))) {
                            final String value = contact.optString(getString(R.string.jsonFieldValue));
                            phoneView.setText(value);
                            phoneBlock.setVisibility(View.VISIBLE);
                            phoneIcon.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    String uri = String.format("tel:%s", value);
                                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(uri));
                                    getActivity().startActivity(intent);
                                }
                            });
                        } else if (type.equals(getString(R.string.jsonContactEmail))) {
                            final String value = contact.optString(getString(R.string.jsonFieldValue));
                            emailView.setText(value.substring(3, value.length() - 1));
                            emailBlock.setVisibility(View.VISIBLE);
                            emailIcon.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    String uri = String.format("mailto:%s", value.substring(3, value.length() - 1));
                                    Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse(uri));
                                    getActivity().startActivity(intent);
                                }
                            });
                        } else if (type.equals(getString(R.string.jsonContactWeb))) {
                            final String value = contact.optString(getString(R.string.jsonFieldValue));
                            webView.setText((value.substring(3, value.length() - 1)).replace("http://", ""));
                            webBlock.setVisibility(View.VISIBLE);
                            webIcon.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(value.substring(3, value.length() - 1)));
                                    getActivity().startActivity(intent);
                                }
                            });
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

    public void appendFavouriteListener(View rootView) {
        ImageButton imageView = (ImageButton) rootView.findViewById(R.id.favoriteIcon);
        imageView.setClickable(true);
        Bundle bundle = getActivity().getIntent().getExtras();
        CompanyModel com = new CompanyModel(bundle.getLong("id"), bundle.getInt("icon"), bundle.getString("name"), bundle.getString("location"));
        DatabaseHandler databaseHandler = new DatabaseHandler(this.getActivity().getApplicationContext(), com);
        imageView.setOnClickListener(databaseHandler);
        if (databaseHandler.isInFavourites(bundle.getLong("id"))) imageView.setImageResource(R.drawable.ic_favorite_on);
        databaseHandler.insertCompanyAsHistory(com);
    }
}
