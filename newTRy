// prog -> {classDecl} {funcDef} 'program' funcBody ';'
prog -> classDeclRepeat funcDefRepeat 'program' funcBody ';'
// classDecl -> 'class' 'id' [':' 'id' {',' 'id'}] '{' {varDecl} {funcDecl} '}' ';'
classDecl -> 'class' 'id' [':' 'id' {',' 'id'}] '{' varDeclRepeat funcDeclRepeat '}' ';'
funcDecl -> type 'id' '(' fParams ')' ';'
funcHead -> type ['id' 'sr'] 'id' '(' fParams ')'
funcDef -> funcHead funcBody ';'
// funcBody -> '{' {varDecl} {statement} '}'
funcBody -> '{' varDeclRepeat statementRepeat '}'
// varDecl -> type 'id' {arraySize} ';'
varDecl -> type 'id' arraySizeRepeat ';'
statement -> assignStat ';'
 | 'if' '(' expr ')' 'then' statBlock 'else' statBlock ';'
 | 'for' '(' type 'id' assignOp expr ';' relExpr ';' assignStat ')' statBlock ';'
 | 'get' '(' variable ')' ';'
 | 'put' '(' expr ')' ';'
 | 'return' '(' expr ')' ';'
assignStat -> variable assignOp expr
// statBlock -> '{' {statement} '}' | statement | EPSILON
statBlock -> '{' statementRepeat '}' | statement | EPSILON
expr -> arithExpr | relExpr
relExpr -> arithExpr relOp arithExpr
arithExpr -> arithExpr addOp term | term
sign -> '+' | '-'
term -> term multOp factor | factor
factor -> variable
 | functionCall
 | 'intNum' | 'floatNum'
 | '(' arithExpr ')'
 | 'not' factor
 | sign factor
 // variable -> {idnest} 'id' {indice}
variable -> idnestRepeat 'id' indiceRepeat
// functionCall -> {idnest} 'id' '(' aParams ')'
functionCall -> idnestRepeat 'id' '(' aParams ')'
// idnest -> 'id' {indice} '.'
idnest -> 'id' indiceRepeat '.'
 | 'id' '(' aParams ')' '.'
indice -> '[' arithExpr ']'
arraySize -> '[' 'intNum' ']'
type -> 'int' | 'float' | 'id'
// fParams -> type 'id' {arraySize} {fParamsTail} | EPSILON
fParams -> type 'id' arraySizeRepeat fParamsTailRepeat | EPSILON

// aParams -> expr {aParamsTail} | EPSILON
aParams -> expr aParamsTailRepeat | EPSILON

// fParamsTail -> ',' type 'id' {arraySize}
fParamsTail -> ',' type 'id' arraySizeRepeat

aParamsTail -> ',' expr
assignOp -> '='
relOp -> 'eq' | 'neq' | 'lt' | 'gt' | 'leq' | 'geq'
addOp -> '+' | '-' | 'or'
multOp -> '*' | '/' | 'and'

--------------------------------------------------
classDeclRepeat ->  classDecl classDeclRepeat | EPSILON
funcDefRepeat -> funcDef funcDefRepeat | EPSILON
funcDeclRepeat -> funcDecl funcDeclRepeat | EPSILON
varDeclRepeat -> varDecl varDeclRepeat | EPSILON
statementRepeat -> statement statementRepeat | EPSILON
idnestRepeat -> idnest idnestRepeat | EPSILON
indiceRepeat -> indice indiceRepeat | EPSILON
aParamsTailRepeat -> aparamsTail aParamsTailRepeat | EPSILON
arraySizeRepeat -> arraySize arraySizeRepeat | EPSILON
fParamsTailRepeat -> fParamsTail fParamsTailRepeat  | EPSILON
