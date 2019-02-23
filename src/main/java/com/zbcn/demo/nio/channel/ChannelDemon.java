package com.zbcn.demo.nio.channel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * NIO 学习demon
 *
 * @author zbcn
 * @date 2019/1/31 11:18
 */
public class ChannelDemon {

    private static FileChannel inChannel;

    static{
        RandomAccessFile accessFile = null;
        try {
            accessFile = new RandomAccessFile("D://test/nio.txt", "rw");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        inChannel = accessFile.getChannel();
    }
    public static void channelTest(){
        try {
            RandomAccessFile accessFile = new RandomAccessFile("D://test/nio.txt","rw");
            FileChannel inChannel = accessFile.getChannel();
            //create buffer with capacity of 48 bytes
            //要想获得一个Buffer对象首先要进行分配。 每一个Buffer类都有一个allocate方法。
            ByteBuffer buf = ByteBuffer.allocate(48);
            int read = inChannel.read(buf);////read into buffer.
            while(read != -1){
                System.out.println("Read " + read);
                //buf.flip() 的调用，首先读取数据到Buffer，然后反转Buffer,接着再从Buffer中读取数据。
                buf.flip();////make buffer ready for read

                while (buf.hasRemaining()){//判断potision -> limit 之间是否存在elements
                    System.out.print((char) buf.get());//// read 1 byte at a time
                }
                buf.clear(); //make buffer ready for writing
                read = inChannel.read(buf);
            }
            accessFile.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 分散
     * Scattering Reads在移动下一个buffer前，必须填满当前的buffer，这也意味着它不适用于动态消息(译者注：消息大小不固定)。换句话说，
     * 如果存在消息头和消息体，消息头必须完成填充（例如 128byte），Scattering Reads才能正常工作。
     */
    public static void scatterReadDemon(){
        ByteBuffer header = ByteBuffer.allocate(128);
        ByteBuffer body   = ByteBuffer.allocate(1024);
        ByteBuffer[] bufferArray = { header, body };
        try {
            //read()方法按照buffer在数组中的顺序将从channel中读取的数据写入到buffer，当一个buffer被写满后，channel紧接着向另一个buffer中写。
            //Scattering Reads在移动下一个buffer前，必须填满当前的buffer，这也意味着它不适用于动态消息(译者注：消息大小不固定)。换句话说，如果存在消息头和消息体，消息头必须完成填充（例如 128byte），Scattering Reads才能正常工作。
            long read = inChannel.read(bufferArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * buffers数组是write()方法的入参，write()方法会按照buffer在数组中的顺序，
     * 将数据写入到channel，注意只有position和limit之间的数据才会被写入。
     * 因此，如果一个buffer的容量为128byte，但是仅仅包含58byte的数据，那么这58byte的数据将被写入到channel中。
     * 因此与Scattering Reads相反，Gathering Writes能较好的处理动态消息。
     */
    public static void gatherWriterDemon(){
        ByteBuffer header = ByteBuffer.allocate(128);
        ByteBuffer body   = ByteBuffer.allocate(1024);
        ByteBuffer[] bufferArray = { header, body };
        try {
            long write = inChannel.write(bufferArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * FileChannel的transferFrom()方法可以将数据从源通道传输到FileChannel中
     */
    public static void transferFromDemon(){
        try {
            RandomAccessFile fromFile = new RandomAccessFile("D://test/from.txt","rw");
            FileChannel fromFileChannel = fromFile.getChannel();
            RandomAccessFile toFile = new RandomAccessFile("D://test/to.txt","rw");
            FileChannel toFileChannel = toFile.getChannel();
            long position = 0;
            long count = fromFileChannel.size();
            long l = toFileChannel.transferFrom(fromFileChannel,position, count);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * transferTo()方法将数据从FileChannel传输到其他的channel中
     */
    public static void transferToDemon(){
        try {
            RandomAccessFile fromFile = new RandomAccessFile("D://test/from.txt","rw");
            FileChannel fromFileChannel = fromFile.getChannel();
            RandomAccessFile toFile = new RandomAccessFile("D://test/to.txt","rw");
            FileChannel toFileChannel = toFile.getChannel();
            long position = 0;
            long count = fromFileChannel.size();
            long l = fromFileChannel.transferTo(position, count, toFileChannel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public  static void selectorDemon(){
        try {
            ServerSocketChannel socketChannel = ServerSocketChannel.open();
            //与Selector一起使用时，Channel必须处于非阻塞模式下。
            socketChannel.configureBlocking(false);
            Selector selector = Selector.open();
            //阻塞到至少有一个通道在你注册的事件上就绪了。
            int select = selector.select();
            //某个线程调用select()方法后阻塞了，即使没有通道已经就绪，也有办法让其从select()方法返回。
            // 只要让其它线程在第一个线程调用select()方法的那个对象上调用Selector.wakeup()方法即可。阻塞在select()方法上的线程会立马返回。
            //如果有其它线程调用了wakeup()方法，但当前没有线程阻塞在select()方法上，下个调用select()方法的线程会立即“醒来（wake up）”
            Selector wakeup = selector.wakeup();
            //用完Selector后调用其close()方法会关闭该Selector，且使注册到该Selector上的所有SelectionKey实例无效。通道本身并不会关闭。
            selector.close();

            //注意register()方法的第二个参数。这是一个“interest集合”，意思是在通过Selector监听Channel时对什么事件感兴趣。
            //Connect\Accept\Read\Write
            //如果你对不止一种事件感兴趣，那么可以用“位或”操作符将常量连接起来，如下：int interestSet = SelectionKey.OP_READ | SelectionKey.OP_WRITE;
            SelectionKey register = socketChannel.register(selector, SelectionKey.OP_READ);
            //SelectionKey register 包含：interest集合、ready集合、Channel、Selector
            int interestSet = register.interestOps();
            boolean isInterestedInAccept  = (interestSet & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT;
            int isInterestedInConnect = interestSet & SelectionKey.OP_CONNECT;
            int isInterestedInRead    = interestSet & SelectionKey.OP_READ;
            int isInterestedInWrite   = interestSet & SelectionKey.OP_WRITE;
            //从SelectionKey访问Channel和Selector很简单
            Channel channel  = register.channel();
            selector = register.selector();

            //附加的对象:可以将一个对象或者更多信息附着到SelectionKey上，这样就能方便的识别某个给定的通道。
            Object o = new Object();
            register.attach(o);
            Object attachedObj = register.attachment();
            //还可以在用register()方法向Selector注册Channel的时候附加对象。
            SelectionKey key = socketChannel.register(selector, SelectionKey.OP_READ, o);
            //一旦调用了select()方法，并且返回值表明有一个或更多个通道就绪了，然后可以通过调用selector的selectedKeys()方法，访问“已选择键集（selected key set）”中的就绪通道
            Set selectedKeys = selector.selectedKeys();
            Iterator iterator = selectedKeys.iterator();
            while (iterator.hasNext()){
                SelectionKey  next = (SelectionKey )iterator.next();
                if(next.isAcceptable()){
                    // a connection was accepted by a ServerSocketChannel.
                }else if(next.isConnectable()){
                    // a connection was established with a remote server.
                }else if(next.isReadable()){
                    // a channel is ready for reading
                }else if(next.isWritable()){
                    // a channel is ready for writing
                }
                //注意每次迭代末尾的keyIterator.remove()调用。Selector不会自己从已选择键集中移除SelectionKey实例。
                // 必须在处理完通道时自己移除。下次该通道变成就绪时，Selector会再次将其放入已选择键集中。
                iterator.remove();
            }



        } catch (IOException e) {
            e.printStackTrace();
        }

    }






    public static void main(String[] args) {
        channelTest();
    }
}
