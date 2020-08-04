#列表推导式由包含一个表达式的括号组成
#表达式后面跟随一个 for 子句，之后可以有零或多个 for 或 if 子句。
#结果是一个列表，由表达式依据其后面的 for 和 if 子句上下文计算而来的结果构成
squares = [x**2 for x in range(10)]
print (squares)

ts = [(x, y) for x in [1,2,3] for y in [3,1,4] if x != y]
print (ts) 

vec = [-4, -2, 0, 2, 4]
print("create a new list with the values doubled")
[x*2 for x in vec]
print (vec)

print("filter the list to exclude negative numbers")
g = [x for x in vec if x >= 0]
print (g)

print("apply a function to all the elements")
a = [abs(x) for x in vec]
print (a)

print("call a method on each element")
freshfruit = ['  banana', '  loganberry ', 'passion fruit  ']
ws = [weapon.strip() for weapon in freshfruit]
print (ws)

print("create a list of 2-tuples like (number, square)")
ts = [(x, x**2) for x in range(6)]
print (ts)

print("flatten a list using a listcomp with two 'for'")
vec = [[1,2,3], [4,5,6], [7,8,9]]
print(vec)
f = [num for elem in vec for num in elem]
print("flat rec:{}".format(f))


print("列表解析中的第一个表达式可以是任何表达式，包括列表解析。")
print("Example: 交换行和列")
matrix = [
    [1, 2, 3, 4],
    [5, 6, 7, 8],
    [9, 10, 11, 12],
]
print (matrix)
tm = [[row[i] for row in matrix] for i in range(4)]
print ("transposed matrix:{}".format(tm))
