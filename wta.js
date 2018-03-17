prog -> classDeclRepeat funcDefRepeat 'program' funcBody ';'
classDecl -> 'class' 'id' [':' 'id' {',' 'id'}] '{' varDeclRepeat funcDeclRepeat '}' ';'
funcDecl -> type 'id' '(' fParams ')' ';'
funcHead -> type ['id' 'sr'] 'id' '(' fParams ')'
funcDef -> funcHead funcBody ';'
funcBody -> '{' varDeclRepeat statementRepeat '}'
varDecl -> type 'id' arraySizeRepeat ';'
statement -> assignStat ';'
 | 'if' '(' expr ')' 'then' statBlock 'else' statBlock ';'
 | 'for' '(' type 'id' assignOp expr ';' relExpr ';' assignStat ')' statBlock ';'
 | 'get' '(' variable ')' ';'
 | 'put' '(' expr ')' ';'
 | 'return' '(' expr ')' ';'
assignStat -> idnestRepeat variable assignOp expr
statBlock -> '{' statementRepeat '}' | statement | EPSILON
expr -> arithExpr relExpr
relExpr -> relOp arithExpr | EPSILON
arithExpr ->  term arithExprTail
arithExprTail -> addOp term arithExprTail | EPSILON
sign -> '+' | '-'
term -> factor termTail
termTail -> multOp factor termTail | EPSILON
factor -> factorFactor
 | 'intNum' | 'floatNum'
 | '(' arithExpr ')'
 | 'not' factor
 | sign factor
factorFactor -> idnestRepeat variableOrfunctionCall
variableOrfunctionCall -> variable | functionCall
variable -> 'id'  indiceRepeat
functionCall -> 'id'  '(' aParams ')'
idnest -> 'id' idnestFactor
idnestRepeat -> idnest idnestRepeat | EPSILON
idnestFactor -> indiceRepeat '.'
  | '(' aParams ')' '.'
indice -> '[' arithExpr ']'
arraySize -> '[' 'intNum' ']'
type -> 'int' | 'float' | 'id'
fParams -> type 'id' arraySizeRepeat fParamsTailRepeat | EPSILON
aParams -> expr aParamsTailRepeat | EPSILON
fParamsTail -> ',' type 'id' arraySizeRepeat
aParamsTail -> ',' expr
assignOp -> '='
relOp -> 'eq' | 'neq' | 'lt' | 'gt' | 'leq' | 'geq'
addOp -> '+' | '-' | 'or'
multOp -> '*' | '/' | 'and'
classDeclRepeat ->  classDecl classDeclRepeat | EPSILON
funcDefRepeat -> funcDef funcDefRepeat | EPSILON
funcDeclRepeat -> funcDecl funcDeclRepeat | EPSILON
varDeclRepeat -> varDecl varDeclRepeat | EPSILON
statementRepeat -> statement statementRepeat | EPSILON
indiceRepeat -> indice indiceRepeat | EPSILON
aParamsTailRepeat -> aParamsTail aParamsTailRepeat | EPSILON
arraySizeRepeat -> arraySize arraySizeRepeat | EPSILON
fParamsTailRepeat -> fParamsTail fParamsTailRepeat  | EPSILON
