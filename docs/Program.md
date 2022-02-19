
```flow
st=>start: 処理開始
e=>end: 処理終了
io1=>operation: Find problem
io3=>subroutine: Solve problem
cond=>condition: Is OK?
sub1=>operation: Make output
op1=>operation: Find not enough
io2=>operation: continue

st->io1->io3->cond
cond(yes)->sub1->op1->e
cond(no)->io2(right)->io3

```

# I found why the flow didn't work well.

This is OK.
![](image/Program/1641733276990.png)

You may notice that there is strange mark at the begining of line 16.
![](image/Program/1641733292930.png)

I copied the three marks from line 2 to line 16, then it worked!
![](image/Program/1641733383147.png)

So I also added "Contine" to the upper flow chart.
![](image/Program/1641733177690.png)