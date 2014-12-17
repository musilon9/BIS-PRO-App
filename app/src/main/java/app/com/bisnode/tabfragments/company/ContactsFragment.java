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

import java.util.ArrayList;
import java.util.Locale;

import app.com.bisnode.R;
import app.com.bisnode.fakedata.FakeSearch;
import app.com.bisnode.objects.Company;
import app.com.bisnode.onclicklisteners.DatabaseHandler;
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
        setBasicContent(rootView, com);
        setAddressContent(rootView, com);
        setPhoneContent(rootView, com);
        setEmailContent(rootView, com);
        setWebContent(rootView, com);

        return rootView;
    }

    private void setBasicContent(View v, Company com) {
        TextView companyName = (TextView) v.findViewById(R.id.companyName);
        companyName.setText(com.getName());
        TextView companyICO = (TextView) v.findViewById(R.id.companyICO);
        companyICO.setText(getString(R.string.ICO_label) + " " + com.getIC());
        TextView companyDIC = (TextView) v.findViewById(R.id.companyDIC);
        companyDIC.setText(getString(R.string.DIC_label) + " " + com.getDIC());
    }

    private void setAddressContent(View rootView, final Company com) {
        LinearLayout block = (LinearLayout) rootView.findViewById(R.id.addressBlock);
        TextView title = (TextView) block.findViewById(R.id.title_blockContact);
        title.setText(getString(R.string.address_sectionTitle));
        ImageButton icon = (ImageButton) block.findViewById(R.id.contact_imgButton);
        icon.setImageResource(R.drawable.ic_map);
        TextView address = (TextView) block.findViewById(R.id.info_blockContact);
        // TODO change to sth better, like list view?
        address.setText(String.format("%s\n%s, %s", com.getAddress(),
                com.getZip(), com.getCity()));

        // start Google Maps to show address on map
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uri = String.format(new Locale("cs", "CZ"), "geo:0,0q=%s, %s",
                        com.getAddress(), com.getCity());
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                getActivity().startActivity(intent);
            }
        });
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
        // TODO change to sth better, like list view?
        for (int i = 0; i < numb.size(); i++) {
            phoneNumbers += numb.get(i) + (i == numb.size() - 1 ? "" : "\n");
        }
        numbers.setText(phoneNumbers);

        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uri = String.format("tel:%s", com.getPhoneNumbers().get(0)); // TODO show dialog to choose number
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(uri));
                getActivity().startActivity(intent);
            }
        });
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

        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uri = String.format("mailto:%s", com.getEmails().get(0)); // TODO show dialog to choose address
                Intent intent = new Intent(Intent.ACTION_SEND, Uri.parse(uri));
                getActivity().startActivity(intent);
            }
        });
    }

    private void setWebContent(View rootView, final Company com) {
        LinearLayout block = (LinearLayout) rootView.findViewById(R.id.webBlock);
        TextView title = (TextView) block.findViewById(R.id.title_blockContact);
        title.setText(getString(R.string.web_sectionTitle));
        ImageButton icon = (ImageButton) block.findViewById(R.id.contact_imgButton);
        icon.setImageResource(R.drawable.ic_web);
        TextView web = (TextView) block.findViewById(R.id.info_blockContact);
        web.setText(com.getWebAddress());

        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uri = com.getWebAddress(); // TODO show dialog to choose web address
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                getActivity().startActivity(intent);
            }
        });
    }

    public void setListeners(Company com, View rootView) {
        ImageButton imageView = (ImageButton) rootView.findViewById(R.id.mapIcon);
        imageView.setClickable(true);
        DatabaseHandler databaseHandler = new DatabaseHandler(this.getActivity().getApplicationContext(), com);
        imageView.setOnClickListener(databaseHandler);
        databaseHandler.insertCompanyAsHistory(com);
    }
}
