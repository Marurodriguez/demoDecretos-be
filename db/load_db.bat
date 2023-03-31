echo LOAD vdig ESTA SEGURO???
pause 
pause
mysqldump -uroot -p1357911 vdig>vdig_tmp.sql
mysql -uroot -p1357911 vdig<vdig.sql
pause