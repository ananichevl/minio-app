import React, {ChangeEvent, useState} from "react";
import {Box, Button, FormControl, Grid, InputLabel, MenuItem, Select, TextField} from "@mui/material";
import BucketItemsList, {ListItemData} from "./BucketItemsList";
import {styled} from '@mui/material/styles';

const VisuallyHiddenInput = styled('input')({
    clip: 'rect(0 0 0 0)',
    clipPath: 'inset(50%)',
    height: 1,
    overflow: 'hidden',
    position: 'absolute',
    bottom: 0,
    left: 0,
    whiteSpace: 'nowrap',
    width: 1,
});

export const MainPage = () => {
    const [objectName, setObjectName] = useState<string>('');
    const [sortField, setSortField] = useState<string>('objectName');
    const [items, setItems] = useState<ListItemData[]>([]);

    const handleSearch = () => {
        fetch(`/items?` + new URLSearchParams({
            objectName: objectName,
            sortField: sortField,
        })).then(response => response.json()).then(data => setItems(data));
    }

    const uploadFile = (e: ChangeEvent<HTMLInputElement>) => {
        if (e.target.files && e.target.files[0]) {
            const data = new FormData();
            data.append('file', e.target.files[0], e.target.files[0].name);
            fetch('/item/upload', {
                method: "POST",
                body: data,
            }).then(response => console.log(response));
        }
    }

    return (
        <Box p={2}>
            <Grid container spacing={2} alignItems="center">
                <Grid item xs={1}>
                    <Button
                        component="label"
                        role={undefined}
                        variant="contained"
                        color="primary"
                        tabIndex={-1}
                    >
                        Upload
                        <VisuallyHiddenInput type="file" onChange={uploadFile}/>
                    </Button>
                </Grid>
                <Grid item xs={8}>
                    <TextField
                        label="Search"
                        variant="outlined"
                        fullWidth
                        onChange={(event) => setObjectName(event.target.value)}
                    />
                </Grid>
                <Grid item xs={1}>
                    <Button
                        variant="contained"
                        color="primary"
                        fullWidth
                        onClick={handleSearch}
                    >Search
                    </Button>
                </Grid>
                <Grid item xs={2}>
                    <FormControl variant="standard" sx={{m: 1, minWidth: 120}}>
                        <InputLabel id="sort-select-label">SortField</InputLabel>
                        <Select
                            labelId="sort-select-label"
                            id="sort-select"
                            value={sortField}
                            label="SortField"
                            onChange={(e) => setSortField(e.target.value)}
                        >
                            <MenuItem value={'objectName'}>ObjectName</MenuItem>
                            <MenuItem value={'size'}>Size</MenuItem>
                        </Select>
                    </FormControl>
                </Grid>
            </Grid>
            {/*            <List>
                <ListItem >
                    <ListItemText primary={"1231241241241"} />
                </ListItem>
                {searchResults.map(result => (
                    <ListItem key={result.id}>
                        <ListItemText primary={result.name} />
                    </ListItem>
                ))}
            </List>*/}
            <BucketItemsList items={items}/>
        </Box>
    )
}

/*

<Box p={2}>
      <Grid container spacing={2} alignItems="center">
        <Grid item xs={9}>
          <TextField
            label="Search"
            variant="outlined"
            fullWidth
            value={searchTerm}
            onChange={(event) => setSearchTerm(event.target.value)}
          />
        </Grid>
        <Grid item xs={3}>
          <Button variant="contained" color="primary" fullWidth onClick={handleSearch}>Search</Button>
        </Grid>
      </Grid>
      <List>
        {searchResults.map(result => (
          <ListItem key={result.id}>
            <ListItemText primary={result.name} />
          </ListItem>
        ))}
      </List>
    </Box>

    <Paper>
                    <Card elevation={3} sx={{ maxWidth: 300, height: 400 }}>
                        <CardActionArea>
                            <CardMedia
                                component="img"
                                height="240"
                                image="http://placekitten.com/200/300"
                            />
                            <CardContent>
                                <Typography gutterBottom variant="body1" textAlign={"left"}>
                                    блаблабла
                                </Typography>
                            </CardContent>
                        </CardActionArea>
                    </Card>
                </Paper>

*/
