# RecycleviewCountTimer
![image](https://github.com/crazyqiang/RecycleviewCountTimer/blob/master/timer.gif) 

下拉然后上拉，由于会复用导致要显示的数据又被初始化成最开始的数据，这里给出的解决方法是通过添加时间戳来解决，加载出数据时通过System.currentTimeMillis()得到开始时间getDataTime，在onBindViewHolder里每次要显示数据时通过System.currentTimeMillis() - getDataTime得到时间戳，这样每次JavaBean里的时间减去时间戳就是正确的时间了！

