

# Mission

Datamap is a simple interface to let several users build a simple data
table collaboratively.

Datamap aims at being used as an upstream tool for Dataroom and other
similar data exposure tools.

-   <https://github.com/MTES-MCT/dataroom>
-   <https://mtes-mct.github.io/dataroom/>


# Requirements

-   [ ] Create a user by using an email address.
-   [ ] Restrict user creation by matching against allowed emails adresses.
-   [ ] A user can CRUD a list of data sets (for which she’s an admin).
-   [ ] A list admin can accept/add/remove users from a list.
-   [ ] An authorized user can CRUD data sets to a list.
-   [ ] Each list is exposed as `json`: `https://[service]/[list].json`


# Schema of a data set

<table border="2" cellspacing="0" cellpadding="6" rules="groups" frame="hsides">


<colgroup>
<col  class="org-left" />

<col  class="org-left" />
</colgroup>
<thead>
<tr>
<th scope="col" class="org-left">Column</th>
<th scope="col" class="org-left">Example</th>
</tr>
</thead>

<tbody>
<tr>
<td class="org-left">Name of the dataset</td>
<td class="org-left">&#xa0;</td>
</tr>


<tr>
<td class="org-left">Description</td>
<td class="org-left">&#xa0;</td>
</tr>


<tr>
<td class="org-left">Type of service</td>
<td class="org-left">&#xa0;</td>
</tr>


<tr>
<td class="org-left">Name of service</td>
<td class="org-left">&#xa0;</td>
</tr>


<tr>
<td class="org-left">Public policy</td>
<td class="org-left">&#xa0;</td>
</tr>


<tr>
<td class="org-left">Source</td>
<td class="org-left">&#xa0;</td>
</tr>


<tr>
<td class="org-left">Keyword</td>
<td class="org-left">&#xa0;</td>
</tr>


<tr>
<td class="org-left">License</td>
<td class="org-left">&#xa0;</td>
</tr>


<tr>
<td class="org-left">Format</td>
<td class="org-left">&#xa0;</td>
</tr>


<tr>
<td class="org-left">Date</td>
<td class="org-left">&#xa0;</td>
</tr>
</tbody>
</table>


# MVPs


## 1

-   [ ] User email registration
-   [ ] Only one list of data sets
-   [ ] Add a data set
-   [ ] expose as json


## 2

-   [ ] Restrict email registration
-   [ ] Remove/edit a data set


## 3

-   [ ] Add/edit/remove lists of data sets

