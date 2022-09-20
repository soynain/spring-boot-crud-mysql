# spring-boot-crud-mysql
Repo for practicing more spring, 
I've learned about beans and autowired, two concepts that were difficult to learn for me some time ago. 
Java has been my favorite language and I'm proud that I can finally handle spring essentials.

Less than a crud, this will be a practice for File Upload with spring and some
animation for uploading progress bar.

I will not use any front end framework for this, just pure backend

It's a simple interface, where we can upload the files, you can upload multiple files at the same time,
because of the aproach I made using multipart[] array, I cannot calculate individually
the status progress of the upload, or at least I havent found a way. The only way to do that
would be doing the uploads at once,sequentially.

![image](https://user-images.githubusercontent.com/78714792/191142986-92d15de1-0c17-48d9-9445-0d7cf829ce0c.png)

![image](https://user-images.githubusercontent.com/78714792/191143017-c86db339-3c96-4d01-a6af-250ab313ca5d.png)

As you can see, there's a drag and drop section, code for that module is still pending, because I've already
done one in another repository, so it's no prioritary for me for now.

![image](https://user-images.githubusercontent.com/78714792/191143141-575d28ba-9927-4add-b0b2-40af139ba9cb.png)

You can upload any amount of files that do not exceed in total the weight of 50mb, that can be modified from the backend.

![image](https://user-images.githubusercontent.com/78714792/191143223-95a85377-707d-41d6-a787-fb23aa873be3.png)

Files will be uploaded to this directory
![image](https://user-images.githubusercontent.com/78714792/191143272-4b93598b-bf28-42e0-a76f-401e04219c02.png)

So, for now there's still two things left to be codified:
-Status bar (because I didn't use multipart with XHMLHttpRequest in vaine)

-Drag and drop
But for me is priority to learn spring.
