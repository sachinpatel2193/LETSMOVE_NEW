public class CustomAdapterPostList extends BaseAdapter {

    private Context context;
    UserBean bean = new UserBean();
    ArrayList<UserBean> arrayList = new java.util.ArrayList<UserBean>();
    private ImageView image_user;

    private ImageView image_user_online;
    String user_name;
    String status;
    private TextView textview_user_name;

    public CustomAdapterUserList(Context context, ArrayList<UserBean> arrayList) {
        // TODO Auto-generated constructor stub
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return arrayList.size();
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(com.example.solve.R.layout.custom_userlist, null);
        }

        image_user = (ImageView)convertView.findViewById(com.example.solve.R.id.imageView_user_pic);
        textview_user_name = (TextView)convertView.findViewById(com.example.solve.R.id.textView_user_name);
        image_user_online = (ImageView)convertView.findViewById(com.example.solve.R.id.imageView_user_online);



        if (bean != null) {
            bean = arrayList.get(position);

            user_name = bean.getNAME();
            status = bean.getSTATUS();

            textview_user_name.setText(user_name);

            if(status.equals("1")){
                image_user_online.setImageResource(R.drawable.online);
            }
            else{

                image_user_online.setImageResource(R.drawable.offline);
            }


        }

        return convertView;
    }
}