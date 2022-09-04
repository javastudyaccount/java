# Java study

1. ``git clone https://github.com/javastudyaccount/java.git``
   ![img](image/readme/1643452961806.png)
   ![img](image/readme/1643453118536.png)
   ![img](image/readme/1643453165583.png)
2. ``git add .``
3. ``git status``
   ![img](image/readme/1643453252345.png)
4. ``git commit -m comment``
   ![img](image/readme/1643453339273.png)

   ![img](image/readme/1643453466588.png)
   ![img](image/readme/1643453497552.png)

   ![img](image/readme/1643453565286.png)
5. ``git push``

   ![img](image/readme/1643453601368.png)`<br/>`
   ![](image/readme/1643453625436.png)`<br/>`
   javastudyaccount/javastudy123

   ![](image/readme/1643453685482.png)`<br/>`
   1344161724@qq.com
   c_ada59b6

   ![](image/readme/1643453708024.png)`<br/>`
   ![](image/readme/1643453750620.png)`<br/>`

   ![](image/readme/1643453767771.png)`<br/>`
6. ``git pull``
   ![](image/readme/1643454047390.png)

### How to split and combine files
https://superuser.com/questions/80081/how-to-split-and-combine-files
#### combine files
`copy /b example.ext.001+example.ext.002+example.ext.003+example.ext.004 example.ext`

#### Cygwin (basic install), Bash shell
`dd if=archive.tar bs=512M | xz -e9fc | split -b4000m - /destination/path/archive_split.`

`dd if=dbunit2.7.0.zip bs=512M | split -b5m - ./dbunit2.7.0_split.`

Omit the xz pipe block if your archive is already compressed.

#### To combine your archive together:

`cat archive_split.* > archive`
