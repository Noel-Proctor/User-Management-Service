import { useNavigate } from "react-router"
import { Button } from '@mui/material'


const Unauthorised = () => {

    const navigate = useNavigate();

    const goBack = () => {
        navigate(-1)
    }

    return (
        <>
            <div>Unauthorised</div>
            <div>
                <Button onClick={goBack}> Go Back </Button>
            </div >
        </>

    )
}

export default Unauthorised