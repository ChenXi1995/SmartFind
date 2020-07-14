# SmartFind   

使用方式:
在Activity or Fragment 中 
onCreate or onCreateView  绑定视图后
Injector.inject(this, this);


然后 在类中声明
 @FindView(R.id.recyclerview)
    RecyclerView recyclerView;
    
    
    
    
